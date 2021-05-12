package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.liftable.{
  LiftableCaseClassImpls,
  LiftableEnumerationImpls
}
import com.github.ivellien.pgquery.parser.nodes._

trait LiftableNode
    extends LiftableCaseClassImpls
    with LiftableEnumerationImpls {

  import c.universe._

  private def lift[T](t: T)(implicit l: Liftable[T]) = l(t)

  implicit val _liftableValue: Liftable[values.Value] = {
    case n: values.NodeInteger => lift[values.NodeInteger](n)
    case n: values.NodeString  => lift[values.NodeString](n)
  }

  implicit val _liftableNode: Liftable[Node] = {
    case n: A_Const            => lift[A_Const](n)
    case n: A_Expr             => lift[A_Expr](n)
    case n: Alias              => lift[Alias](n)
    case n: AlterTableCmd      => lift[AlterTableCmd](n)
    case n: AlterTableStmt     => lift[AlterTableStmt](n)
    case n: BoolExpr           => lift[BoolExpr](n)
    case n: CaseExpr           => lift[CaseExpr](n)
    case n: CaseWhen           => lift[CaseWhen](n)
    case n: ColumnDef          => lift[ColumnDef](n)
    case n: ColumnRef          => lift[ColumnRef](n)
    case n: CreatedbStmt       => lift[CreatedbStmt](n)
    case n: CreateStmt         => lift[CreateStmt](n)
    case n: DropdbStmt         => lift[DropdbStmt](n)
    case n: DropStmt           => lift[DropStmt](n)
    case n: FuncCall           => lift[FuncCall](n)
    case n: InsertStmt         => lift[InsertStmt](n)
    case n: IntoClause         => lift[IntoClause](n)
    case n: JoinExpr           => lift[JoinExpr](n)
    case n: values.NodeInteger => lift[values.NodeInteger](n)
    case n: values.NodeString  => lift[values.NodeString](n)
    case n: NullTest           => lift[NullTest](n)
    case n: ParamRef           => lift[ParamRef](n)
    case n: RangeVar           => lift[RangeVar](n)
    case n: RawStmt            => lift[RawStmt](n)
    case n: ResTarget          => lift[ResTarget](n)
    case n: SelectStmt         => lift[SelectStmt](n)
    case n: SortBy             => lift[SortBy](n)
    case n: SubLink            => lift[SubLink](n)
    case n: TruncateStmt       => lift[TruncateStmt](n)
    case n: TypeName           => lift[TypeName](n)
  }
}
