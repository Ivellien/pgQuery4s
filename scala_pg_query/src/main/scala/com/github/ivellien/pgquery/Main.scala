package com.github.ivellien.pgquery

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "SELECT DISTINCT ON (age) age FROM dummy_table ORDER BY x ASC, y DESC"

    val parser: PgQueryParser = new PgQueryParser

    println(parser.json(input))
    println(parser.parseTree(input))
    println(parser.prettify(input))
  }
}
