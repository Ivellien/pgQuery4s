package com.github.ivellien.pgquery.core.expr

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.enums._
import com.github.ivellien.pgquery.parser.nodes._
import com.github.ivellien.pgquery.parser.nodes.values.{A_Star, NodeString}
import org.scalatest.Inside.inside
import org.scalatest._

class ExprInterpolatorTests extends FunSuite with Matchers {
  test("ColumnRef expression test") {
    val expr = expr"columnName"
    expr should matchPattern {
      case ResTarget(_, _, Some(ColumnRef(_, _)), _) =>
    }
  }

  test("A_Expr expression test") {
    val expr: ResTarget = expr"value = 5"
    expr should matchPattern {
      case ResTarget(_, _, Some(A_Expr(A_Expr_Kind.AExprOp, _, _, _, _)), _) =>
    }
  }

  test("LIKE expression test") {
    val expr = expr"value LIKE 5"
    expr should matchPattern {
      case ResTarget(
          _,
          _,
          Some(A_Expr(A_Expr_Kind.AexprLike, _, _, _, _)),
          _
          ) =>
    }
  }

  test("ILIKE expression test") {
    val expr = expr"value ILIKE 5"
    expr should matchPattern {
      case ResTarget(
          _,
          _,
          Some(A_Expr(A_Expr_Kind.AexprIlike, _, _, _, _)),
          _
          ) =>
    }
  }

  test("NOT LIKE expression test") {
    val expr = expr"value NOT LIKE low"
    expr should matchPattern {
      case ResTarget(
          _,
          _,
          Some(A_Expr(A_Expr_Kind.AexprLike, _, _, _, _)),
          _
          ) =>
    }
  }

  test("NOT ILIKE expression test") {
    val expr = expr"value NOT ILIKE 5"
    expr should matchPattern {
      case ResTarget(
          _,
          _,
          Some(A_Expr(A_Expr_Kind.AexprIlike, _, _, _, _)),
          _
          ) =>
    }
  }

  test("ALIAS expression test") {
    val expr = expr"column_name AS alias_name"
    expr should matchPattern {
      case ResTarget(
          Some("alias_name"),
          _,
          Some(ColumnRef(List(NodeString("column_name")), _)),
          _
          ) =>
    }
  }

  test("* expression test") {
    val expr = expr"*"
    expr should matchPattern {
      case ResTarget(_, _, Some(ColumnRef(List(A_Star), _)), _) =>
    }
  }

  test("AND expression test") {
    val expr = expr"x = 5 and y = 3"
    expr should matchPattern {
      case ResTarget(_, _, Some(BoolExpr(BoolExprType.AndExpr, _, _)), _) =>
    }
  }

  test("OR expression test") {
    val expr = expr"x = 5 or y = 3"
    expr should matchPattern {
      case ResTarget(_, _, Some(BoolExpr(BoolExprType.OrExpr, _, _)), _) =>
    }
  }

  test("NOT expression test") {
    val expr = expr"not y = 3"
    expr should matchPattern {
      case ResTarget(_, _, Some(BoolExpr(BoolExprType.NotExpr, _, _)), _) =>
    }
  }

  // ORDER BY is expected at the end of query, so it evaluates differently from expressions
//  test("ORDER BY expression test") {
//    val expr = expr"ORDER BY x"
//    val innerValue = validateTypeOfExprResult[SortBy](expr)
//  }

  test("IS NULL expression test") {
    val expr = expr"x IS NULL"
    expr should matchPattern {
      case ResTarget(_, _, Some(NullTest(_, NullTestType.IsNull, _, _)), _) =>
    }
  }

  test("IS NOT NULL expression test") {
    val expr = expr"x IS NOT NULL"
    expr should matchPattern {
      case ResTarget(
          _,
          _,
          Some(NullTest(_, NullTestType.IsNotNull, _, _)),
          _
          ) =>
    }
  }

  test("Func call expression test") {
    val expr = expr"MIN(columnName)"
    expr should matchPattern {
      case ResTarget(_, _, Some(_: FuncCall), _) =>
    }
  }
}
