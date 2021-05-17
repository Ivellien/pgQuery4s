package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.{
  ParsingError,
  PgQueryError,
  PgQueryParser
}
import com.github.ivellien.pgquery.parser.PgQueryParser.PgQueryResult
import com.github.ivellien.pgquery.parser.nodes._

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import com.typesafe.scalalogging.LazyLogging

class MacrosImpl(val c: whitebox.Context)
    extends LiftableNode
    with LazyLogging {
  import c.universe._

  def parseExprMacro(args: c.Expr[Any]*): c.Expr[ResTarget] = {
    val result = validateResult(
      parseCompileTime(args, isSqlExpression = true)
    )
    c.Expr[ResTarget](result.tree)
  }

  def parseQueryMacro(args: c.Expr[Any]*): c.Expr[Node] = {
    validateResult(
      parseCompileTime(args, isSqlExpression = false)
    )
  }

  private def parseCompileTime(
      args: Seq[c.Expr[Any]],
      isSqlExpression: Boolean
  ): PgQueryResult[c.Expr[Node]] = {
    val lift = implicitly[Liftable[Node]]

    val sqlLiteralsStrings: List[String] = c.prefix.tree match {
      case q"""
        com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator(
        scala.StringContext.apply(..${sqlLiterals: List[Tree]})
        )
      """ =>
        sqlLiterals.collect {
          case Literal(Constant(part: String)) => part
        }
      case _ =>
        return Left(ParsingError(s"Invalid prefix tree."))
    }

    val varsByPos: Map[Int, Tree] = args
      .map(_.tree)
      .zipWithIndex
      .map {
        case (v, i) => i + 1 -> v
      }
      .toMap

    val renamedVars: List[String] = args.zipWithIndex.map {
      case (_, i) => s"$$${i + 1}"
    }.toList

    val t: ParamRefTransformer = new ParamRefTransformer(varsByPos)

    val query: String =
      intersperse(sqlLiteralsStrings, renamedVars).mkString

    val resultNode: PgQueryResult[Node] = if (isSqlExpression) {
      PgQueryParser.parse("SELECT " + query).flatMap(extractExpression)
    } else {
      PgQueryParser.parse(query)
    }

    resultNode match {
      case Right(n: Node) =>
        Right(c.Expr(t.transform(c.Expr(lift { n }).tree)))
      case Left(error: PgQueryError) =>
        Left(error)
    }
  }

  private def extractExpression(node: Node): PgQueryResult[ResTarget] = {
    node match {
      case RawStmt(_, _, Some(select: SelectStmt)) =>
        select.targetList match {
          case (rt: ResTarget) :: _ => Right(rt)
          case o =>
            Left(
              ParsingError(
                s"Error while extracting expression. Expected ResTarget, got $o"
              )
            )
        }
      case o =>
        Left(
          ParsingError(
            s"Error while extracting expression. Expected ResTarget, got $o"
          )
        )
    }
  }

  private def intersperse[A](a: List[A], b: List[A]): List[A] = a match {
    case first :: rest => first :: intersperse(b, rest)
    case _             => b
  }

  private def validateResult(
      pgQueryResult: PgQueryResult[c.Expr[Node]]
  ): c.Expr[Node] = {
    pgQueryResult match {
      case Right(node) =>
        c.Expr[ResTarget](node.tree)
      case Left(error: ParsingError) =>
        c.abort(
          c.enclosingPosition,
          s"Error while parsing the interpolated string. \n" +
            s"Error while parsing - ${error.errorMessage}."
        )
      case Left(error) =>
        c.abort(
          c.enclosingPosition,
          s"Error while parsing the interpolated string. \n" +
            s"$error."
        )
    }
  }

  private class ParamRefTransformer(varsByPos: Map[Int, Tree])
      extends Transformer {
    override def transform(tree: Tree): Tree = {
      tree match {
        case q"ParamRef(${Literal(Constant(constant: Int))}, ${_})" =>
          varsByPos(constant)
        case _ =>
          super.transform(tree)
      }
    }
  }
}
