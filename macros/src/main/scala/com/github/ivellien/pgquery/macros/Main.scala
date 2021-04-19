package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.macros.Macros.parse_compile
import com.github.ivellien.pgquery.parser.PgQueryParser

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "select $1 WHERE $3"
    println(PgQueryParser.prettify(input))
  }
}
