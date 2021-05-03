package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.values.{
  A_Star,
  NodeInteger,
  NodeString
}
import com.typesafe.scalalogging.{LazyLogging, Logger}
import io.circe.generic.extras.Configuration
import io.circe.{ACursor, Decoder, HCursor}

abstract class Node {
  def query: String
}

object Node extends LazyLogging {
  def optionToQuery(node: Option[Node]): String =
    node.map(_.query).getOrElse("")

  implicit val circeConfig: Configuration = Configuration.default.withDefaults

  implicit val decoder: Decoder[Node] = (c: HCursor) => {
    c.keys match {
      case Some(keys) if keys.nonEmpty =>
        val key: String = keys.head

        NodeTag.withName(key) match {
          case NodeTag.T_A_Const        => c.as[A_Const]
          case NodeTag.T_A_Expr         => c.as[A_Expr]
          case NodeTag.T_A_Star         => c.downField(key).as[A_Star.type]
          case NodeTag.T_Alias          => c.as[Alias]
          case NodeTag.T_AlterTableCmd  => c.as[AlterTableCmd]
          case NodeTag.T_AlterTableStmt => c.as[AlterTableStmt]
          case NodeTag.T_BoolExpr       => c.as[BoolExpr]
          case NodeTag.T_CaseExpr       => c.as[CaseExpr]
          case NodeTag.T_CaseWhen       => c.as[CaseWhen]
          case NodeTag.T_ColumnDef      => c.as[ColumnDef]
          case NodeTag.T_ColumnRef      => c.as[ColumnRef]
          case NodeTag.T_CreatedbStmt   => c.as[CreatedbStmt]
          case NodeTag.T_CreateStmt     => c.as[CreateStmt]
          case NodeTag.T_DropdbStmt     => c.as[DropdbStmt]
          case NodeTag.T_DropStmt       => c.as[DropStmt]
          case NodeTag.T_FuncCall       => c.as[FuncCall]
          case NodeTag.T_InsertStmt     => c.as[InsertStmt]
          case NodeTag.T_IntoClause     => c.as[IntoClause]
          case NodeTag.T_JoinExpr       => c.as[JoinExpr]
          case NodeTag.T_Integer        => c.as[NodeInteger]
          case NodeTag.T_String         => c.as[NodeString]
          case NodeTag.T_NullTest       => c.as[NullTest]
          case NodeTag.T_ParamRef       => c.as[ParamRef]
          case NodeTag.T_RangeVar       => c.as[RangeVar]
          case NodeTag.T_RawStmt        => c.as[RawStmt]
          case NodeTag.T_ResTarget      => c.as[ResTarget]
          case NodeTag.T_SelectStmt     => c.as[SelectStmt]
          case NodeTag.T_SortBy         => c.as[SortBy]
          case NodeTag.T_SubLink        => c.as[SubLink]
          case NodeTag.T_TruncateStmt   => c.as[TruncateStmt]
          case NodeTag.T_TypeName       => c.as[TypeName]
          case _ =>
            logger.error(s"Unsupported yet - $key")
            Right(EmptyNode)
        }
      case _ => Right(EmptyNode)
    }
  }
}

case object EmptyNode extends Node {
  override def query: String = ""
}
