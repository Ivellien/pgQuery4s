package com.github.ivellien.pgquery

object Main {
  def main(args: Array[String]): Unit = {
    val parser = new PgQueryParser(
      "SELECT x FROM y WHERE x < 2 and x > 0 or x = 1"
    )

    println(parser.originalQuery)
    println(parser.json)
    println(parser.parseTree)
    println(parser.prettify)
  }
}
