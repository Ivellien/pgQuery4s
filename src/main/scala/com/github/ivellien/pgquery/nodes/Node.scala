package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.NodeTag
import com.typesafe.scalalogging.{LazyLogging, Logger}
import io.circe.generic.extras.Configuration
import io.circe.{ACursor, Decoder, HCursor}

abstract class Node {
  def query: String
}

object Node extends LazyLogging {

  implicit val circeConfig: Configuration = Configuration.default.withDefaults

  implicit val decoder: Decoder[Node] = (c: HCursor) => {
    c.keys match {
      case Some(keys) if keys.nonEmpty =>
        val key: String = keys.head
        val value: ACursor = c.downField(key)

        NodeTag.withName(key) match {
          case NodeTag.T_SelectStmt => value.as[SelectStmt]
          case NodeTag.T_RawStmt    => value.as[RawStmt]
          case NodeTag.T_ResTarget  => value.as[ResTarget]
          case NodeTag.T_RangeVar   => value.as[RangeVar]
          case NodeTag.T_ColumnRef  => value.as[ColumnRef]
          case NodeTag.T_String     => value.as[NodeString]
          case NodeTag.T_Integer    => value.as[NodeInteger]
          case NodeTag.T_A_Expr     => value.as[A_Expr]
          case NodeTag.T_A_Const    => value.as[A_Const]
          case NodeTag.T_A_Star     => value.as[A_Star.type]
          case NodeTag.T_BoolExpr   => value.as[BoolExpr]
          case NodeTag.T_SortBy     => value.as[SortBy]
          case NodeTag.T_NullTest   => value.as[NullTest]
          case NodeTag.T_FuncCall   => value.as[FuncCall]
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
