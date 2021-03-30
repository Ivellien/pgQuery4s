package Nodes

import CursorSyntax.MyACursor
import io.circe.ACursor

case class SelectStmt(distinctClause: Option[Node],
                      intoClause: Option[Node],
                      targetList: Iterable[Node],
                      fromClause: Iterable[Node],
                      whereClause: Option[Node],
                      groupClause: Option[Node],
                      havingClause: Option[Node],
                      op: Option[Int]) extends Node {
  override def toQuery(): String = {
    val targets: String = targetList.map(x => x.toQuery()).mkString(",")
    val from: String = fromClause.map(x => x.toQuery()).mkString(",")
    val where: String = if(whereClause.isEmpty) "" else whereClause.get.toQuery()
    "SELECT " + targets + " " +
    "FROM " + from + " " +
    "WHERE " + where
  }
}

object SelectStmt {
  def apply(cursor: ACursor): Node = {
    SelectStmt(
      distinctClause = cursor.getNodeOption("distinctClause"),
      intoClause = cursor.getNodeOption("intoClause"),
      targetList = cursor.getIterableNode("targetList"),
      fromClause = cursor.getIterableNode("fromClause"),
      whereClause = cursor.getNodeOption("whereClause"),
      groupClause = cursor.getNodeOption("groupClause"),
      havingClause = cursor.getNodeOption("havingClause"),
      op = cursor.getIntFieldOption("op")
    )
  }
}