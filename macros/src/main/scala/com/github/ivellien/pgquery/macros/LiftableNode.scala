package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.liftable.{
  LiftableCaseClassImpls,
  LiftableEnumerationImpls
}
import com.github.ivellien.pgquery.parser.nodes

trait LiftableNode
    extends LiftableCaseClassImpls
    with LiftableEnumerationImpls {

  import c.universe._

  private def lift[T](t: T)(implicit l: Liftable[T]) = l(t)

  implicit val _liftableValue: Liftable[nodes.values.Value] = {
    case n: nodes.values.NodeInteger => lift[nodes.values.NodeInteger](n)
    case n: nodes.values.NodeString  => lift[nodes.values.NodeString](n)
  }

  implicit val _liftableNode: Liftable[nodes.Node] = {
    case n: nodes.A_Const            => lift[nodes.A_Const](n)
    case n: nodes.A_Expr             => lift[nodes.A_Expr](n)
    case n: nodes.Alias              => lift[nodes.Alias](n)
    case n: nodes.AlterTableCmd      => lift[nodes.AlterTableCmd](n)
    case n: nodes.AlterTableStmt     => lift[nodes.AlterTableStmt](n)
    case n: nodes.BoolExpr           => lift[nodes.BoolExpr](n)
    case n: nodes.CaseExpr           => lift[nodes.CaseExpr](n)
    case n: nodes.CaseWhen           => lift[nodes.CaseWhen](n)
    case n: nodes.ColumnDef          => lift[nodes.ColumnDef](n)
    case n: nodes.ColumnRef          => lift[nodes.ColumnRef](n)
    case n: nodes.CreatedbStmt       => lift[nodes.CreatedbStmt](n)
    case n: nodes.CreateStmt         => lift[nodes.CreateStmt](n)
    case n: nodes.DropdbStmt         => lift[nodes.DropdbStmt](n)
    case n: nodes.DropStmt           => lift[nodes.DropStmt](n)
    case n: nodes.FuncCall           => lift[nodes.FuncCall](n)
    case n: nodes.InsertStmt         => lift[nodes.InsertStmt](n)
    case n: nodes.IntoClause         => lift[nodes.IntoClause](n)
    case n: nodes.JoinExpr           => lift[nodes.JoinExpr](n)
    case n: nodes.values.NodeInteger => lift[nodes.values.NodeInteger](n)
    case n: nodes.values.NodeString  => lift[nodes.values.NodeString](n)
    case n: nodes.NullTest           => lift[nodes.NullTest](n)
    case n: nodes.ParamRef           => lift[nodes.ParamRef](n)
    case n: nodes.RangeVar           => lift[nodes.RangeVar](n)
    case n: nodes.RawStmt            => lift[nodes.RawStmt](n)
    case n: nodes.ResTarget          => lift[nodes.ResTarget](n)
    case n: nodes.SelectStmt         => lift[nodes.SelectStmt](n)
    case n: nodes.SortBy             => lift[nodes.SortBy](n)
    case n: nodes.SubLink            => lift[nodes.SubLink](n)
    case n: nodes.TruncateStmt       => lift[nodes.TruncateStmt](n)
    case n: nodes.TypeName           => lift[nodes.TypeName](n)
  }
}
