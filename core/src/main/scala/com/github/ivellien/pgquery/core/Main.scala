package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.PgQueryInterpolator._
import com.github.ivellien.pgquery.macros.Macros

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "SELECT $1 WHERE $3"

    val from = "FROM x"
    println(q"SELECT $$1 $from")
    val x = "jeans"
    val y = "clothes = 5"
    def query(select: String, where: String): String =
      pr"SELECT x, $select, y WHERE $where"
    println(query("jeans", "clothes = 5"))
    val macroRes = Macros.parse_compile("abcdef")
    // When uncommented, this will produce an error at compile-time, as we
    // only implemented a case for an Int literal, not a variable:
//    val n = 10
//    println(factorial(n))
//    println(PgQueryParser.wrapper.pgQueryParse(input))
//    println(PgQueryParser.json(input))
//    println(PgQueryParser.parseTree(input))
//    println(PgQueryParser.prettify(input))
  }
}
