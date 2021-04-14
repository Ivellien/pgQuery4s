package com.github.ivellien.pgquery.prettify

import com.github.ivellien.pgquery.PgQueryParser

object PrettifyTestHelper {
  def prettifyTest(query: String): Unit = {
    val parser = new PgQueryParser
    assert(parser.prettify(query) == query)
  }
}
