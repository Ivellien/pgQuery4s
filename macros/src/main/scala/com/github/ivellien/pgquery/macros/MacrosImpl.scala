package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.enums.A_Expr_Kind
import com.github.ivellien.pgquery.parser.nodes._

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import com.github.ivellien.pgquery.parser.nodes.values.NodeString
import com.typesafe.scalalogging.LazyLogging

import scala.reflect.runtime.{universe => u}
import u._

class MacrosImpl(val c: whitebox.Context)
    extends LiftableNode
    with LazyLogging {

  def intersperse[A](a: List[A], b: List[A]): List[A] = a match {
    case first :: rest => first :: intersperse(b, rest)
    case _             => b
  }

  def parseExprMacro(args: c.Expr[Any]*): c.Expr[Node] = {
    parseCompileTime(args, true)
  }

  def parseQueryMacro(args: c.Expr[Any]*): c.Expr[Node] = {
    parseCompileTime(args, false)
  }

  private def parseCompileTime(
      args: Seq[c.Expr[Any]],
      isSqlExpression: Boolean
  ): c.Expr[Node] = {
    import c.universe._

    val lift = implicitly[Liftable[Node]]

    val sqlLiteralsStrings: List[String] = c.prefix.tree match {
      case q"""
        com.github.ivellien.pgquery.core.PgQueryInterpolator.PgInterpolator(
        scala.StringContext.apply(..${sqlLiterals: List[Tree]})
        )
      """ =>
        sqlLiterals.collect {
          case Literal(Constant(part: String)) => part
        }
      case _ =>
        logger.error("Could not recognize prefix.")
        return c.Expr(lift { NodeString("Invalid prefix.") })
    }

    val varsByPos = args
      .map(_.tree)
      .zipWithIndex
      .map {
        case (v, i) => i + 1 -> v
      }
      .toMap

    val renamedVariables = args.zipWithIndex.map {
      case (_, i) => s"$$${i + 1}"
    }.toList

    val t = new Transformer() {
      override def transform(tree: Tree): Tree = {
        tree match {
          case q"ParamRef(..$pos)" =>
            pos.headOption match {
              case Some(Literal(Constant(constant: Int))) =>
                varsByPos(constant)
              case _ =>
                super.transform(tree)
            }
          case _ =>
            super.transform(tree)
        }
      }
    }

    val resultParseTree = isSqlExpression match {
      case true =>
        extractExpression(
          PgQueryParser
            .parseTree(
              intersperse(
                sqlLiteralsStrings
                  .updated(0, "SELECT " + sqlLiteralsStrings.head),
                renamedVariables
              ).mkString("")
            )
            .getOrElse(List.empty)
        )
      case false =>
        PgQueryParser
          .parseTree(
            intersperse(sqlLiteralsStrings, renamedVariables).mkString("")
          )
          .getOrElse(List.empty)
          .headOption
    }

    resultParseTree match {
      case Some(n: Node) =>
        c.Expr(t.transform(c.Expr(lift { n }).tree))
      case _ =>
        logger.error("Failed parsing of query.")
        c.Expr(lift {
          NodeString("Failed parsing of query.")
        })
    }
  }

  private def extractExpression(node: List[Node]): Option[Node] = {
    node.headOption match {
      case Some(
          RawStmt(
            _,
            _,
            Some(
              stmt: SelectStmt
            )
          )
          ) =>
        stmt.targetList.headOption
      case _ => None
    }
  }
}
