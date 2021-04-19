package com.github.ivellien.pgquery.parser.prettify

import com.github.ivellien.pgquery.parser.PgQueryParser

object PrettifyTestHelper {
  def prettifyTest(query: String): Unit = {
    assert(PgQueryParser.prettify(query) == query)
  }
}
