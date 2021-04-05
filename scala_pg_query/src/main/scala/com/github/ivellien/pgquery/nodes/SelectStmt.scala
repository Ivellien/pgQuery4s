package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though

@ConfiguredJsonCodec(decodeOnly = true)
case class SelectStmt(
    distinctClause: List[Node] = List.empty,
    intoClause: Node = EmptyNode(),
    targetList: List[Node] = List.empty,
    fromClause: List[Node] = List.empty,
    whereClause: Node = EmptyNode(),
    groupClause: Node = EmptyNode(),
    havingClause: Node = EmptyNode(),
    windowClause: List[Node] = List.empty,
    sortClause: List[Node] = List.empty,
    limitOffset: Node = EmptyNode(),
    limitCount: Node = EmptyNode(),
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

  private def fromQuery: String = {
    if (fromClause.isEmpty) ""
    else s" FROM ${fromClause.map(x => x.query).mkString(", ")}"
  }

  private def whereQuery: String = {
    if (whereClause.query.isEmpty) "" else s" WHERE ${whereClause.query}"
  }

  private def sortByQuery: String = {
    if (sortClause.isEmpty) ""
    else s" ORDER BY ${sortClause.map(x => x.query).mkString(", ")}"
  }
}
