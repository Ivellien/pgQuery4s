package com.github.ivellien.pgquery.parser

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "SELECT $1 WHERE $2"

    println(PgQueryParser.wrapper.pgQueryParse(input))
    println(PgQueryParser.json(input))
    println(PgQueryParser.parse(input))
    println(PgQueryParser.prettify(input))
  }
}
