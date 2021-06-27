package com.github.ivellien.pgquery.parser.prettify

import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.nodes.EmptyNode
import org.scalatest.FunSuite

class InsertStmtTests extends FunSuite {
  test("INSERT INTO statement") {
    PrettifyTestHelper.prettifyTest(
      "INSERT INTO customers (customername, city, country) VALUES ('Cardinal', 'Stavanger', 'Norway')"
    )

    PrettifyTestHelper.prettifyTest(
      "INSERT INTO films_recent (name, date_prod) VALUES ('The Horse Motion', '1878-06-11'), ('The Other Movie', '2021-06-22')"
    )
  }
}
