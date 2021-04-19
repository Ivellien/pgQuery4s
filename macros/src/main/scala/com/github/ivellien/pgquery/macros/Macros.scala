package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.PgQueryParser

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

object Macros {
  def parse_compile(query: String): String =
    macro Macros.parse_impl

  def parse_impl(c: Context)(query: c.Expr[String]): c.Expr[String] = {
    import c.universe._
    println("compile time !")
    query match {
      case Expr(Literal(Constant(queryValue: String))) =>
        c.Expr(Literal(Constant("After macro: " + queryValue)))
      case _ =>
        println("Passed value is not a string.")
        c.Expr(Literal(Constant("Not a string.")))
    }

    // TODO java.library.path is not correctly set at compile time?
//    val result = PgQueryParser.prettify(query.toString)
//    c.Expr(Literal(Constant(result)))
  }

  def normal(str: String): String = str
}
