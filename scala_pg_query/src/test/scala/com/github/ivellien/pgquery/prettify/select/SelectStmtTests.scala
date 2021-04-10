package com.github.ivellien.pgquery.prettify.select

import com.github.ivellien.pgquery.prettify.PrettifyTestHelper
import org.scalatest.FunSuite

class SelectStmtTests extends FunSuite {
  test("Simple SELECT statement") {
    PrettifyTestHelper.prettifyTest("SELECT select_list FROM table_name")
  }

  test("Simple SELECT statement with WHERE") {
    PrettifyTestHelper.prettifyTest("SELECT select_list FROM table_name WHERE x = 5")
  }

  test("Simple SELECT statement with *") {
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers")
  }

  test("Simple SELECT statement with DISTINCT") {
    PrettifyTestHelper.prettifyTest("SELECT DISTINCT age FROM dummy_table ORDER BY 1")
  }

  test("Simple SELECT statement with DISTINCT ON") {
    PrettifyTestHelper.prettifyTest("SELECT DISTINCT ON (age) age FROM dummy_table ORDER BY 1")
  }

  test("Simple SELECT statement with ORDER BY") {
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x")
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x ASC")
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x DESC")
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x ASC, y DESC, z")
  }

}
