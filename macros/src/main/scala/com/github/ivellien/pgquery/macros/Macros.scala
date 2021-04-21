package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.liftable.LiftableCaseClassImpls
import com.github.ivellien.pgquery.parser.nodes._

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import com.github.ivellien.pgquery.parser.nodes.NodeString

object Macros {
  def parse_compile(query: String): Node = macro MacrosImpl.parse_impl
}

class MacrosImpl(val c: whitebox.Context)
    extends LiftableCaseClassImpls
    with LiftableNode {

  def parse_impl(query: c.Expr[String]): c.Expr[Node] = {
    import c.universe._

    println(s"compile time !")
    val lift = implicitly[Liftable[Node]]

    query match {
      case Expr(Literal(Constant(queryValue: String))) =>
        c.Expr(lift(NodeString(queryValue)))
      case _ =>
        println("Passed value is not a string.")
        c.Expr(lift(NodeString("Not a string.")))
    }
  }
}
