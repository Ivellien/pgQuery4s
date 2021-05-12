package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.PgQueryInterpolator.PgInterpolator
import com.github.ivellien.pgquery.parser.nodes.ResTarget

object Main {
  def main(args: Array[String]): Unit = {
    val y = expr"x"
    val str: String = "abc LIKE \"abc\""

    println(query"SELECT $str")
    println(query"SELEC x")
    println(ctq"SELECT *")

    println(expr"x = 4".query)
    println(query"SELECT x = 5".query)
  }
}
