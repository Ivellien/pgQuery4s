package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.PgQueryParser

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

object Macros {
  def parse_impl(
      c: Context
  )(sc: c.Expr[StringContext], args: c.Expr[Any]*): c.Expr[String] = {
    import c.universe._
    println("compile time !")
    args match {
      case Expr(Literal(Constant(queryValue: String))) =>
        c.Expr(Literal(Constant("After macro: " + queryValue)))
      case _ =>
        println("Passed value is not a string.")
        c.Expr(Literal(Constant("Not a string.")))
    }

    val result = PgQueryParser.prettify(args.toString)
    c.Expr(Literal(Constant(result)))
  }
}
