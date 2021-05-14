package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.PgQueryParser.PgQueryResult
import com.github.ivellien.pgquery.parser.nodes._

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import com.github.ivellien.pgquery.parser.nodes.values.NodeString
import com.typesafe.scalalogging.LazyLogging

import scala.reflect.internal.util.Position

class MacrosImpl(val c: whitebox.Context)
    extends LiftableNode
    with LazyLogging {
  import c.universe._

  def parseExprMacro(args: c.Expr[Any]*): c.Expr[ResTarget] = {
    val result = parseCompileTime(args, isSqlExpression = true)
    result.tree match {
      case q"ResTarget(..${_})" => c.Expr[ResTarget](result.tree)
      case _ =>
        c.abort(
          c.enclosingPosition,
          s"Error while running compile time check. \n" +
            s"Result from expression isn't type ResTarget."
        )
    }
  }

  def parseQueryMacro(args: c.Expr[Any]*): c.Expr[Node] = {
    parseCompileTime(args, isSqlExpression = false)
  }

  private def parseCompileTime(
      args: Seq[c.Expr[Any]],
      isSqlExpression: Boolean
  ): c.Expr[Node] = {
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
        c.abort(
          c.enclosingPosition,
          s"Error while running compile time check. \n" +
            s"Invalid prefix tree."
        )
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
      intersperse(sqlLiteralsStrings, renamedVars).mkString("")
    val resultNode: Option[Node] = if (isSqlExpression) {
      extractExpression(PgQueryParser.parseTree("SELECT " + query))
    } else {
      PgQueryParser.parseTree(query).toOption
    }

    resultNode match {
      case Some(n: Node) =>
        c.Expr(t.transform(c.Expr(lift { n }).tree))
      case _ =>
        c.abort(
          c.enclosingPosition,
          s"Error while running compile time check. \n" +
            s"Failed parsing of the query - \'$query\'"
        )
    }
  }

  private def extractExpression(
      queryResult: PgQueryResult[Node]
  ): Option[ResTarget] = {
    queryResult match {
      case Right(RawStmt(_, _, Some(select: SelectStmt))) =>
        select.targetList.headOption
      case _ =>
        c.abort(
          c.enclosingPosition,
          s"Error while running compile time check. \n" +
            s"Extracting expression from parse tree failed."
        )
    }
  }

  private def intersperse[A](a: List[A], b: List[A]): List[A] = a match {
    case first :: rest => first :: intersperse(b, rest)
    case _             => b
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
