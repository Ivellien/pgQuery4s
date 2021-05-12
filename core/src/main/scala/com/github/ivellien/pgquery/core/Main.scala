package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.PgQueryInterpolator.PgInterpolator

object Main {
  def main(args: Array[String]): Unit = {
    val str: String = "abc LIKE \"abc\""

    println(query"SELECT $str")
    println(query"SELEC x")

    println(ctq"SELECT x, y")
    println(ctq"SELEC x")

    println(expr"x = 4".query)
    println(query"SELECT x = 5".query)
  }
}
