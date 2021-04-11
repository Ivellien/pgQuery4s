package com.github.ivellien.pgquery.prettify

import com.github.ivellien.pgquery.PgQueryParser

object PrettifyTestHelper {
  def prettifyTest(query: String): Unit = {
    val parser = new PgQueryParser
    println(s"*${parser.prettify(query)}*")
    println(s"*${query}*")
    assert(parser.prettify(query) == query)
  }
}
