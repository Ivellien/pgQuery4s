package com.github.ivellien.pgquery.core.expr

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.enums._
import com.github.ivellien.pgquery.parser.nodes._
import org.scalatest._

import scala.reflect.ClassTag

class ExprInterpolatorTests extends FunSuite {
  test("ColumnRef expression test") {
    val expr = expr"columnName"
    validateTypeOfExprResult[ColumnRef](expr)
  }

  test("A_Expr expression test") {
    val expr: ResTarget = expr"value = 5"
    validateTypeOfExprResult[A_Expr](expr)
  }

  test("LIKE expression test") {
    val expr = expr"value LIKE 5"
    val innerValue = validateTypeOfExprResult[A_Expr](expr)
    assert(innerValue.kind == A_Expr_Kind.AexprLike)
  }

  test("ILIKE expression test") {
    val expr = expr"value ILIKE 5"
    val innerValue = validateTypeOfExprResult[A_Expr](expr)
    assert(innerValue.kind == A_Expr_Kind.AexprIlike)
  }

  test("NOT LIKE expression test") {
    val expr = expr"value NOT LIKE low"
    val innerValue = validateTypeOfExprResult[A_Expr](expr)
    assert(innerValue.kind == A_Expr_Kind.AexprLike)
  }

  test("NOT ILIKE expression test") {
    val expr = expr"value NOT ILIKE 5"
    val innerValue = validateTypeOfExprResult[A_Expr](expr)
    assert(innerValue.kind == A_Expr_Kind.AexprIlike)
  }

  test("ALIAS expression test") {
    val expr = expr"column_name AS alias_name"
    validateTypeOfExprResult[ColumnRef](expr)
    assert(expr.name.contains("alias_name"))
  }

  // Waiting for LiftableCaseObject
//  test("* expression test") {
//    val expr = expr"*"
//    validateTypeOfExprResult[A_Star.type](expr)
//  }

  test("AND expression test") {
    val expr = expr"x = 5 and y = 3"
    val innerValue = validateTypeOfExprResult[BoolExpr](expr)
    assert(innerValue.boolop == BoolExprType.AndExpr)
  }

  test("OR expression test") {
    val expr = expr"x = 5 or y = 3"
    val innerValue = validateTypeOfExprResult[BoolExpr](expr)
    assert(innerValue.boolop == BoolExprType.OrExpr)
  }

  test("NOT expression test") {
    val expr = expr"not y = 3"
    val innerValue = validateTypeOfExprResult[BoolExpr](expr)
    assert(innerValue.boolop == BoolExprType.NotExpr)
  }

  // ORDER BY is expected at the end of query, so it evaluates differently from expressions
//  test("ORDER BY expression test") {
//    val expr = expr"ORDER BY x"
//    val innerValue = validateTypeOfExprResult[SortBy](expr)
//  }

  test("IS NULL expression test") {
    val expr = expr"x IS NULL"
    val innerValue = validateTypeOfExprResult[NullTest](expr)
    assert(innerValue.nulltesttype == NullTestType.IsNull)
  }

  test("IS NOT NULL expression test") {
    val expr = expr"x IS NOT NULL"
    val innerValue = validateTypeOfExprResult[NullTest](expr)
    assert(innerValue.nulltesttype == NullTestType.IsNotNull)
  }

  test("Func call expression test") {
    val expr = expr"MIN(columnName)"
    validateTypeOfExprResult[FuncCall](expr)
  }

  private def validateTypeOfExprResult[T <: Node: ClassTag](
      resTarget: ResTarget
  ): T = {
    println(resTarget)
    val node = resTarget match {
      case ResTarget(_, _, Some(value: T), _) => value
      case _                                  => fail("Node wasn't of ResTarget type.")
    }
    node.asInstanceOf[T]
  }
}
