package com.github.ivellien.pgquery.prettify.select

import com.github.ivellien.pgquery.prettify.PrettifyTestHelper
import org.scalatest.FunSuite

class SelectStmtTests extends FunSuite {
  test("Simple SELECT statement") {
    PrettifyTestHelper.prettifyTest("SELECT select_list FROM table_name")
  }

  test("Simple SELECT statement with WHERE") {
    PrettifyTestHelper.prettifyTest(
      "SELECT select_list FROM table_name WHERE x = 5"
    )
  }

  test("Simple SELECT statement with *") {
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers")
  }

  test("Simple SELECT statement with DISTINCT") {
    PrettifyTestHelper.prettifyTest(
      "SELECT DISTINCT age FROM dummy_table ORDER BY 1"
    )
  }

  test("Simple SELECT statement with DISTINCT ON") {
    PrettifyTestHelper.prettifyTest(
      "SELECT DISTINCT ON (age) age FROM dummy_table ORDER BY 1"
    )
  }

  test("Simple SELECT statement with ORDER BY") {
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x")
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x ASC")
    PrettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x DESC")
    PrettifyTestHelper.prettifyTest(
      "SELECT * FROM customers ORDER BY x ASC, y DESC, z"
    )
  }

  test("Simple SELECT statement with NULL check") {
    PrettifyTestHelper.prettifyTest(
      "SELECT column_names FROM table_name WHERE column_name IS NULL"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT column_names FROM table_name WHERE column_name IS NOT NULL"
    )
  }

  test("Simple SELECT statement with Function calls") {
    PrettifyTestHelper.prettifyTest(
      "SELECT min(column_name) FROM table_name WHERE condition"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT max(column_name) FROM table_name WHERE condition"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT count(column_name) FROM table_name WHERE condition"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT avg(column_name) FROM table_name WHERE condition"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT sum(column_name) FROM table_name WHERE condition"
    )
  }

  test("Simple SELECT statement with LIKE, ILIKE") {
    PrettifyTestHelper.prettifyTest(
      "SELECT column1, column2 FROM table_name WHERE columnn LIKE pattern"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT column1, column2 FROM table_name WHERE columnn NOT LIKE pattern"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT column1, column2 FROM table_name WHERE columnn ILIKE pattern"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT column1, column2 FROM table_name WHERE columnn NOT ILIKE pattern"
    )
  }

  test("Simple SELECT statement with ALIAS") {
    PrettifyTestHelper.prettifyTest(
      "SELECT column_name AS alias_name FROM table_name"
    )
  }

  test("UNION, EXCEPT, INTERSECT of SELECT statements") {
    PrettifyTestHelper.prettifyTest(
      "SELECT column_name(s) FROM table1 UNION SELECT column_name(s) FROM table2"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT column_name(s) FROM table1 EXCEPT SELECT column_name(s) FROM table2"
    )
    PrettifyTestHelper.prettifyTest(
      "SELECT column_name(s) FROM table1 INTERSECT SELECT column_name(s) FROM table2"
    )
  }

  test("Simple SELECT statement with GROUP BY") {
    PrettifyTestHelper.prettifyTest(
      "SELECT count(customerid), country FROM customers GROUP BY country"
    )
  }

  test("Simple SELECT statement with HAVING") {
    PrettifyTestHelper.prettifyTest(
      "SELECT count(customerid), country FROM customers GROUP BY country HAVING count(customerid) > 5"
    )
  }

  test("Simple SELECT INTO") {
    PrettifyTestHelper.prettifyTest(
      "SELECT * INTO customersbackup2017 FROM customers"
    )
  }

  test("SELECT statement with CASE") {
    PrettifyTestHelper.prettifyTest(
      "SELECT customername, city, country FROM customers ORDER BY (CASE WHEN city IS NULL THEN country ELSE city END)"
    )
  }
}
