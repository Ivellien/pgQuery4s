package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.nodes.ResTarget

object Main {
  def main(args: Array[String]): Unit = {
    val str: String = "abc LIKE \"abc\""

    val x = query"SELECT x"
    val res = expr"x=5"

    println(query"SELECT $res")
    res match {
      case r: ResTarget => println(query"SELECT $r")
      case _            => println("Something happened.")
    }

    println(PgQueryParser.parse(s"SELECT $str"))
    println(PgQueryParser.prettify("SELECT x From y where x > 4"))
//    println(query"SELEC x")

//    println(query"SELEC y")

    println(query"SELECT $res")

    println(expr"x = 4".query)
    println(PgQueryParser.parse("SELECT x = 5"))
  }
}
