package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.enums.A_Expr_Kind
import com.github.ivellien.pgquery.parser.nodes._

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import com.github.ivellien.pgquery.parser.nodes.NodeString

object Macros {
  def parse_compile(query: String): Node = macro MacrosImpl.parse_impl
}

class MacrosImpl(val c: whitebox.Context) extends LiftableNode {

  def parse_impl(query: c.Expr[String]): c.Expr[Node] = {
    import c.universe._

    println(s"compile time ! (yet again)")

    val lift = implicitly[Liftable[Node]]

    query match {
      case Expr(Literal(Constant(queryValue: String))) =>
        // arbitrarily complex query value, just to show that CCs, enums and Nodes work
        c.Expr(lift {
          A_Expr(
            A_Expr_Kind.AexprDistinct,
            Nil,
            Some(NodeString(queryValue)),
            None,
            Some(3)
          )
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
