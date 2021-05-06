package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.enums.A_Expr_Kind
import com.github.ivellien.pgquery.parser.nodes._

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import com.github.ivellien.pgquery.parser.nodes.values.NodeString

object Macros {
  def parse_compile(query: String): Node = macro MacrosImpl.parse_impl
}

class MacrosImpl(val c: whitebox.Context) extends LiftableNode {
  def intersperse[A](a: List[A], b: List[A]): List[A] = a match {
    case first :: rest => first :: intersperse(b, rest)
    case _             => b
  }

  def parse_impl(query: c.Expr[String]): c.Expr[Node] = {
    import c.universe._

    println(s"compile time ! (yet again)")
    println(s"$query")

    val lift = implicitly[Liftable[Node]]

    println(query.tree)
    query.tree match {
      case Apply(
          Select(
            Apply(
              _,
              List(
                Apply(
                  Select(_, apply),
                  sqlLiterals
                )
              )
            ),
            _
          ),
          variables
          ) =>
        val variablesMapped: List[String] = variables.zipWithIndex.map {
          case (Ident(x), index) => s"$$${index + 1}"
          case (_, _)            => "varname"
        }
        println(variablesMapped)
        val merged = intersperse(
          sqlLiterals.map(x => s"$x".replace("\"", "")),
          variablesMapped
        )
        println(merged.mkString(""))
        val parseTree: List[Node] = {
          PgQueryParser.parseTree(merged.mkString("")).getOrElse(List.empty)
        }
        println(parseTree)

        c.Expr(lift {
          parseTree.headOption.getOrElse(NodeString("Empty result."))
        })

      case _ =>
        println("Passed value is not a string.")
        c.Expr(lift {
          NodeString("Not a string.")
        })
    }

    // val result = PgQueryParser.prettify(args.toString)
    // c.Expr(Literal(Constant(result)))

  }
}
