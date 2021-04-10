package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class SelectStmt(
    intoClause: Option[Node],
    whereClause: Option[Node],
    groupClause: Option[Node],
    havingClause: Option[Node],
    limitOffset: Option[Node],
    limitCount: Option[Node],
    op: Option[Int],
    distinctClause: List[Node] = List.empty,
    targetList: List[Node] = List.empty,
    fromClause: List[Node] = List.empty,
    windowClause: List[Node] = List.empty,
    sortClause: List[Node] = List.empty,
    lockingClause: List[Node] = List.empty
    // TODO withClause: WithClause = EmptyNode(),
) extends Node {
  override def query: String = {
    s"SELECT $distinctQuery$targetsQuery$fromQuery$whereQuery$sortByQuery"
  }

  private def distinctQuery: String = distinctClause match {
    case Nil             => ""
    case List(EmptyNode) => s"DISTINCT "
    case _ =>
      s"DISTINCT ON (${distinctClause.map(x => x.query).mkString(", ")}) "
  }

  private def targetsQuery: String = {
    targetList.map(x => x.query).mkString(", ")
  }

  private def fromQuery: String = fromClause match {
    case Nil => ""
    case _   => s" FROM ${fromClause.map(node => node.query).mkString(", ")}"
  }

  private def whereQuery: String = {
    whereClause.map(clause => s" WHERE ${clause.query}").getOrElse("")
  }

  private def sortByQuery: String = sortClause match {
    case Nil => ""
    case _   => s" ORDER BY ${sortClause.map(node => node.query).mkString(", ")}"
  }
}
