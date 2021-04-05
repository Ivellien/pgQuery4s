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
    /*
      // TODO
      // ouch both .get and .head might throw an error - this should be type safe as well
      // feel free to rewrite this to smth like

      for {
        key <- c.keys.flatMap(_.headOption).toRight(???)
        value = c.downField(key)
      } yield {
        NodeTag.withName(key) match {
          case ... => ???
        }
      }

      and see what the individual combinators do in the process. For inspiration you can see that I
      rewrote some other error handling this way in some other part of the codebase.
     */

    val key: String = c.keys.get.head
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
      case NodeTag.T_BoolExpr   => value.as[BoolExpr]
      case _ =>
        logger.error(s"Unsupported yet - $key")
        Right(EmptyNode())
    }
  }
}

case class EmptyNode() extends Node {
  override def query: String = ""
}
