package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class SelectStmt(
    intoClause: Option[Node],
    whereClause: Option[Node],
    havingClause: Option[Node],
    limitOffset: Option[Node],
    limitCount: Option[Node],
    op: Option[Int],
    groupClause: List[Node] = List.empty,
    distinctClause: List[Node] = List.empty,
    targetList: List[Node] = List.empty,
    fromClause: List[Node] = List.empty,
    windowClause: List[Node] = List.empty,
    sortClause: List[Node] = List.empty,
    lockingClause: List[Node] = List.empty
    // TODO withClause: WithClause = EmptyNode(),
) extends Node {
  override def query: String = {
    s"SELECT $distinctQuery$targetsQuery$fromQuery$whereQuery$groupQuery$havingQuery$sortByQuery"
  }

  private def distinctQuery: String = distinctClause match {
    case Nil             => ""
    case List(EmptyNode) => s"DISTINCT "
    case _ =>
      s"DISTINCT ON (${distinctClause.map(node => node.query).mkString(", ")}) "
  }

  private def targetsQuery: String = {
    targetList.map(target => target.query).mkString(", ")
  }

  private def fromQuery: String = fromClause match {
    case Nil => ""
    case _   => s" FROM ${fromClause.map(node => node.query).mkString(", ")}"
  }

  private def whereQuery: String = {
    whereClause.map(clause => s" WHERE ${clause.query}").getOrElse("")
  }

  private def havingQuery: String = {
    havingClause.map(clause => s" HAVING ${clause.query}").getOrElse("")
  }

  private def groupQuery: String = groupClause match {
    case Nil => ""
    case _   => s" GROUP BY ${groupClause.map(node => node.query).mkString(", ")}"
  }

  private def sortByQuery: String = sortClause match {
    case Nil => ""
    case _   => s" ORDER BY ${sortClause.map(node => node.query).mkString(", ")}"
  }
}
