package com.github.ivellien.pgquery.prettify

import com.github.ivellien.pgquery.PgQueryParser

object PrettifyTestHelper {
  def prettifyTest(query: String): Unit = {
    val parser = new PgQueryParser
    println("*" + query + "*")
    println("*" + parser.prettify(query) + "*")
    assert(parser.prettify(query) == query)
  }
}
