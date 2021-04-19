package com.github.ivellien.pgquery.parser.prettify

import org.scalatest.FunSuite

class InsertStmtTests extends FunSuite {
  test("INSERT INTO staement") {
    PrettifyTestHelper.prettifyTest(
      "INSERT INTO customers (customername, city, country) VALUES ('Cardinal', 'Stavanger', 'Norway')"
    )
  }
}
