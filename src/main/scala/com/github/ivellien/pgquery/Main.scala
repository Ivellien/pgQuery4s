package com.github.ivellien.pgquery

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "ALTER TABLE persons ADD dateofbirth date"

    val parser: PgQueryParser = new PgQueryParser

    println(parser.wrapper.pgQueryParse(input))
    println(parser.json(input))
    println(parser.parseTree(input))
    println(parser.prettify(input))
  }
}
