package com.github.ivellien.pgquery.prettify

import org.scalatest.FunSuite

class InsertStmtTests extends FunSuite {
  test("INSERT INTO statement") {
    PrettifyTestHelper.prettifyTest(
      "INSERT INTO customers (customername, city, country) VALUES ('Cardinal', 'Stavanger', 'Norway')"
    )
  }
}
