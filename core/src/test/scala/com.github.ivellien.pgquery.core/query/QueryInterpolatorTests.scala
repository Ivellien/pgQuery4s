package com.github.ivellien.pgquery.core.query

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.enums.A_Expr_Kind
import com.github.ivellien.pgquery.parser.nodes._
import org.scalatest.FunSuite

import scala.reflect.ClassTag

class QueryInterpolatorTests extends FunSuite {
  test("SELECT query test") {
    val column1 = expr"column1"
    val column2 = expr"column2"
    val column3 = expr"column3"
    val query = query"SELECT $column1, $column2, $column3"
    val selectStmt = validateStatementOfQuery[SelectStmt](query)
    assert(selectStmt.targetList.size == 3)
  }

  test("INSERT INTO value query test") {
    val value = expr"Cardinal"
    val query =
      query"INSERT INTO tableName (customername, city, country) VALUES ($value, 'Stavanger', 'Norway')"
    validateStatementOfQuery[InsertStmt](query)
  }

  test("WHERE with A_Expr query test") {
    val expr = expr"x = 5"
    val query = query"SELECT x WHERE $expr"
    val select = validateStatementOfQuery[SelectStmt](query)
    assert(
      select.whereClause
        .getOrElse(EmptyNode)
        .asInstanceOf[ResTarget]
        .value
        .getOrElse(EmptyNode)
        .asInstanceOf[A_Expr]
        .kind == A_Expr_Kind.AExprOp
    )
  }

  test("WHERE with LIKE query test") {
    val expr = expr"x LIKE abc"
    val query = query"SELECT x WHERE $expr"
    val select = validateStatementOfQuery[SelectStmt](query)
    assert(
      select.whereClause
        .getOrElse(EmptyNode)
        .asInstanceOf[ResTarget]
        .value
        .getOrElse(EmptyNode)
        .asInstanceOf[A_Expr]
        .kind == A_Expr_Kind.AexprLike
    )
  }

  test("WHERE with ILIKE query test") {
    val expr = expr"x ILIKE abc"
    val query = query"SELECT x WHERE $expr"
    val select = validateStatementOfQuery[SelectStmt](query)
    assert(
      select.whereClause
        .getOrElse(EmptyNode)
        .asInstanceOf[ResTarget]
        .value
        .getOrElse(EmptyNode)
        .asInstanceOf[A_Expr]
        .kind == A_Expr_Kind.AexprIlike
    )
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

  private def validateStatementOfQuery[T <: Node: ClassTag](
      rawStmt: Node
  ): T = {
    val stmt = rawStmt match {
      case RawStmt(_, _, Some(stmt: T)) => stmt
      case _                            => fail("Node wasn't of RawStmt type.")
    }
    stmt.asInstanceOf[T]
  }

}
