package Nodes

import Enums.NodeTag
import io.circe.ACursor

abstract class Node {
  def toQuery(): String
}

object Node {
  def apply(cursor: ACursor): Node = {
    val key: String = cursor.keys.head.head
    val value: ACursor = cursor.downField(key)

    NodeTag.withName(key) match {
      case NodeTag.T_SelectStmt => SelectStmt(value)
      case NodeTag.T_RawStmt => RawStmt(value)
      case NodeTag.T_ResTarget => ResTarget(value)
      case NodeTag.T_RangeVar => RangeVar(value)
      case NodeTag.T_ColumnRef => ColumnRef(value)
      case NodeTag.T_String => NodeString(value)
      case NodeTag.T_Integer => NodeInteger(value)
      case NodeTag.T_A_Expr => A_Expr(value)
      case NodeTag.T_A_Const => A_Const(value)
      case _ => {
        println("Unsupported yet - " + key)
        EmptyNode()
      }
    }
  }
}

case class EmptyNode() extends Node {
  override def toQuery(): String = ""
}
