package com.github.ivellien.pgquery.macros

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

object Macros {
  def parse_compile(query: String): String =
    macro Macros.parse_impl

  def parse_impl(c: Context)(query: c.Expr[String]): c.Expr[String] = {
    import c.universe._
    println("compile time !")
//    val result = PgQueryParser.parseTree(query.toString()).toString
    c.Expr(Literal(Constant("abc")))
  }
}
