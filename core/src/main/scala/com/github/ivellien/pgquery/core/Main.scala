package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.core.PgQueryInterpolator.RuntimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.ResTarget

object Main {
  def main(args: Array[String]): Unit = {
    val str: String = "abc LIKE \"abc\""

    val res = cte"x=5"

    println(ctq"SELECT $res")
    res match {
      case r: ResTarget => println(ctq"SELECT $r")
      case _            => println("Something happened.")
    }

    println(rtq"SELECT $str")
    println(pr"SELECT x From y where x > 4")
    println(ctq"SELEC x")

    println(ctq"SELECT $res")

    println(cte"x = 4".query)
    println(rtq"SELECT x = 5")
  }
}
