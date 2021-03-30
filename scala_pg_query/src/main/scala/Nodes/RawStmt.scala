package Nodes

import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor

case class RawStmt(
                    stmt: Option[Node],
                    stmt_location: Option[Int],
                    stmt_len: Option[Int],
                  ) extends Node {
  override def toQuery(): String = {
    if (stmt.isEmpty) "" else stmt.get.toQuery()
  }
}

object RawStmt {
  def apply(cursor: ACursor): RawStmt = {
    RawStmt(
      stmt = cursor.getNodeOption("stmt"),
      stmt_location = cursor.getIntFieldOption("stmt_location"),
      stmt_len = cursor.getIntFieldOption("smtm_len")
    )
  }
}
