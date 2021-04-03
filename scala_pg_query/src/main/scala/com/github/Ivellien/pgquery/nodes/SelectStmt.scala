package com.github.Ivellien.pgquery.nodes

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
  override def query: String = {
    val targets: String = targetList.map(x => x.query).mkString(",")
    val from: String = fromClause.map(x => x.query).mkString(",")
    val where: Node = whereClause.getOrElse(EmptyNode())
    "SELECT " + targets + " " +
      "FROM " + from + " " +
      "WHERE " + where.query
  }
}


object SelectStmt {
  implicit val decoder: Decoder[SelectStmt] = deriveDecoder[SelectStmt]
}