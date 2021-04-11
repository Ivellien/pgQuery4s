package com.github.ivellien.pgquery

import org.scalatest.FunSuite

class QueryTests extends FunSuite {
  test("parsing") {
    val input: String = "SELECT x FROM y WHERE x < 2 and x > 0 or x = 1"

    val parser = new PgQueryParser
    assert(parser.prettify(input) == input)
  }
}
