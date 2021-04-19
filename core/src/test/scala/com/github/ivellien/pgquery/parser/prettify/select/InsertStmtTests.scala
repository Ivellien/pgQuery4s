package com.github.ivellien.pgquery.parser.prettify.select

import com.github.ivellien.pgquery.parser.prettify.PrettifyTestHelper
import org.scalatest.FunSuite

class InsertStmtTests extends FunSuite {
  test("INSERT INTO staement") {
    PrettifyTestHelper.prettifyTest(
      "INSERT INTO customers (customername, city, country) VALUES ('Cardinal', 'Stavanger', 'Norway')"
    )
  }
}
