package com.github.ivellien.pgquery

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "SELECT title FROM film WHERE length >= ANY (SELECT max(length) FROM film)"

    val parser: PgQueryParser = new PgQueryParser

    println(parser.wrapper.pgQueryParse(input))
    println(parser.json(input))
    println(parser.parseTree(input))
    println(parser.prettify(input))
  }
}
