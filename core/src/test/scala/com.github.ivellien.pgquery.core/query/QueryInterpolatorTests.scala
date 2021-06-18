package com.github.ivellien.pgquery.core.query

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.enums.A_Expr_Kind
import com.github.ivellien.pgquery.parser.nodes._
import com.github.ivellien.pgquery.core.ImplicitConversions._
import org.scalatest.Inside.inside
import org.scalatest.{FunSuite, Matchers}

class QueryInterpolatorTests extends FunSuite with Matchers {
  test("SELECT query test") {
    val column1 = expr"column1"
    val column2 = expr"column2"
    val column3 = expr"column3"
    val query = query"SELECT $column1, $column2, $column3"
    inside(query) {
      case RawStmt(_, _, Some(selectStmt: SelectStmt)) =>
        selectStmt.targetList.size shouldBe 3
    }
  }

  test("INSERT INTO value query test") {
    val value = "Cardinal"
    val query =
      query"INSERT INTO tableName (customername, city, country) VALUES ($value, 'Stavanger', 'Norway')"
    query should matchPattern {
      case RawStmt(_, _, Some(_: InsertStmt)) =>
    }
  }

  test("WHERE with A_Expr query test") {
    val expr = expr"x = 5"
    val query = query"SELECT x WHERE $expr"
    inside(query) {
      case RawStmt(_, _, Some(selectStmt: SelectStmt)) =>
        inside(selectStmt.whereClause) {
          case Some(resTarget: ResTarget) =>
            resTarget.value should matchPattern {
              case Some(A_Expr(A_Expr_Kind.AExprOp, _, _, _, _)) =>
            }
        }
    }
  }

  test("WHERE with LIKE query test") {
    val expr = expr"x LIKE abc"
    val query = query"SELECT x WHERE $expr"
    inside(query) {
      case RawStmt(_, _, Some(selectStmt: SelectStmt)) =>
        inside(selectStmt.whereClause) {
          case Some(resTarget: ResTarget) =>
            resTarget.value should matchPattern {
              case Some(A_Expr(A_Expr_Kind.AexprLike, _, _, _, _)) =>
            }
        }
    }
  }

  test("WHERE with ILIKE query test") {
    val expr = expr"x ILIKE abc"
    val query = query"SELECT x WHERE $expr"
    inside(query) {
      case RawStmt(_, _, Some(selectStmt: SelectStmt)) =>
        inside(selectStmt.whereClause) {
          case Some(resTarget: ResTarget) =>
            resTarget.value should matchPattern {
              case Some(A_Expr(A_Expr_Kind.AexprIlike, _, _, _, _)) =>
            }
        }
    }
  }

  // Tests for features that are yet to be implemented
  /*
  test("INSERT INTO column name query test") {
    val columnName = expr"customername"
    val query =
      query"INSERT INTO tableName ($columnName, city, country) VALUES ('Cardinal', 'Stavanger', 'Norway')"
    validateStatementOfQuery[InsertStmt](query)
  }

  test("INSERT INTO table name query test") {
    val tableName = expr"tablename"
    val query =
      query"INSERT INTO $tableName (customername, city, country) VALUES ('Cardinal', 'Stavanger', 'Norway')"
    validateStatementOfQuery[InsertStmt](query)
  }
 */
}
