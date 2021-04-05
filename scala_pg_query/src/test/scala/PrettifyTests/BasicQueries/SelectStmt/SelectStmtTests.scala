package PrettifyTests.BasicQueries.SelectStmt

import PrettifyTests.PrettifyTestHelper
import com.github.ivellien.pgquery.PgQueryParser
import org.scalatest.FunSuite

class SelectStmtTests extends FunSuite {
  val prettifyTestHelper = new PrettifyTestHelper
  test("Simple SELECT statement") {
    prettifyTestHelper.prettifyTest("SELECT select_list FROM table_name")
  }

  test("Simple SELECT statement with WHERE") {
    prettifyTestHelper.prettifyTest("SELECT select_list FROM table_name WHERE x = 5")
  }

  test("Simple SELECT statement with *") {
    prettifyTestHelper.prettifyTest("SELECT * FROM customers")
  }

  test("Simple SELECT statement with DISTINCT") {
    prettifyTestHelper.prettifyTest("SELECT DISTINCT age FROM dummy_table ORDER BY 1")
  }

  test("Simple SELECT statement with DISTINCT ON") {
    prettifyTestHelper.prettifyTest("SELECT DISTINCT ON (age) age FROM dummy_table ORDER BY 1")
  }

  test("Simple SELECT statement with ORDER BY") {
    prettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x")
    prettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x ASC")
    prettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x DESC")
    prettifyTestHelper.prettifyTest("SELECT * FROM customers ORDER BY x ASC, y DESC, z")
  }

}
