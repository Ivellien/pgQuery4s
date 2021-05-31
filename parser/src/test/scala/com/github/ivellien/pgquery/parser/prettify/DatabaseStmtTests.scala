package com.github.ivellien.pgquery.parser.prettify

import org.scalatest.FunSuite

class DatabaseStmtTests extends FunSuite {
  test("CREATE DATABASE test") {
    PrettifyTestHelper.prettifyTest("CREATE DATABASE testdb")
  }

  test("DROP DATABASE test") {
    PrettifyTestHelper.prettifyTest("DROP DATABASE testdb")
  }

  test("CREATE TABLE test") {
    PrettifyTestHelper.prettifyTest(
      "CREATE TABLE persons (personid int4, lastname varchar(255), firstname varchar(255), address varchar(255), city varchar(255))"
    )
  }

  test("CREATE TABLE with Constraints test") {
    PrettifyTestHelper.prettifyTest(
      "CREATE TABLE roles (role_id serial PRIMARY KEY, role_name varchar(255) UNIQUE NOT NULL)"
    )
  }

  test("DROP TABLE test") {
    PrettifyTestHelper.prettifyTest("DROP TABLE IF EXISTS shippers")
    PrettifyTestHelper.prettifyTest("DROP TABLE shippers")
    PrettifyTestHelper.prettifyTest("TRUNCATE TABLE shippers")
    PrettifyTestHelper.prettifyTest("DROP TABLE a, b, c")
  }
}
