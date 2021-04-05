package PrettifyTests

import com.github.ivellien.pgquery.PgQueryParser

class PrettifyTestHelper {
  def prettifyTest(query: String): Unit = {
    val parser = new PgQueryParser
    println(s"*${parser.prettify(query)}*")
    println(s"*${query}*")
    assert(parser.prettify(query) == query)
  }
}
