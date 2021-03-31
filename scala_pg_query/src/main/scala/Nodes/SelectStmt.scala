package Nodes

import io.circe._
import io.circe.generic.semiauto._

case class SelectStmt(distinctClause: Option[Node],
                      intoClause: Option[Node],
                      targetList: List[Node],
                      fromClause: List[Node],
                      whereClause: Option[Node],
                      groupClause: Option[Node],
                      havingClause: Option[Node],
                      op: Option[Int]) extends Node {
  override def toQuery(): String = {
    val targets: String = targetList.map(x => x.toQuery()).mkString(",")
    val from: String = fromClause.map(x => x.toQuery()).mkString(",")
    val where: String = if (whereClause.isEmpty) "" else whereClause.get.toQuery()
    "SELECT " + targets + " " +
      "FROM " + from + " " +
      "WHERE " + where
  }
}


object SelectStmt {
  implicit val decoder: Decoder[SelectStmt] = deriveDecoder[SelectStmt]
}