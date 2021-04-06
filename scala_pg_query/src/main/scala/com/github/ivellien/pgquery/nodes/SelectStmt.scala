package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class SelectStmt(
    distinctClause: List[Node] = List.empty,
    intoClause: Option[Node],
    targetList: List[Node] = List.empty,
    fromClause: List[Node] = List.empty,
    whereClause: Option[Node],
    groupClause: Option[Node],
    havingClause: Option[Node],
    windowClause: List[Node] = List.empty,
    sortClause: List[Node] = List.empty,
    limitOffset: Option[Node],
    limitCount: Option[Node],
    lockingClause: List[Node] = List.empty,
    // TODO withClause: WithClause = EmptyNode(),
    op: Option[Int]
) extends Node {
  override def query: String = {
    s"SELECT $distinctQuery$targetsQuery$fromQuery$whereQuery$sortByQuery"
  }

  private def distinctQuery: String = {
    if (distinctClause.isEmpty) ""
    else if (distinctClause.head == EmptyNode()) s"DISTINCT "
    else s"DISTINCT ON (${distinctClause.map(x => x.query).mkString(", ")}) "
  }

  private def targetsQuery: String = {
    targetList.map(x => x.query).mkString(", ")
  }

  private def fromQuery: String = fromClause match {
    case Nil => ""
    case _ => s" FROM ${fromClause.map(node => node.query).mkString(", ")}"
  }

  private def whereQuery: String = {
    whereClause.map(clause => s" WHERE ${clause.query}").getOrElse("")
  }

  private def sortByQuery: String = sortClause match {
    case Nil => ""
    case _ => s" ORDER BY ${sortClause.map(node => node.query).mkString(", ")}"
  }
}
