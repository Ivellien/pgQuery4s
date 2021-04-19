package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.SetOperation
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}

@ConfiguredJsonCodec(decodeOnly = true)
case class SelectStmt(
    intoClause: Option[Node],
    whereClause: Option[Node],
    havingClause: Option[Node],
    limitOffset: Option[Node],
    limitCount: Option[Node],
    op: SetOperation.Value,
    larg: Option[Node],
    rarg: Option[Node],
    valuesLists: List[List[Node]] = List.empty,
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
    valuesLists match {
      case Nil =>
        op match {
          case SetOperation.SetOpNone =>
            s"SELECT $distinctQuery$targetsQuery$intoQuery$fromQuery$whereQuery$groupQuery$havingQuery$sortByQuery$limitCountQuery"
          case _ =>
            s"${optionToQuery(larg)} ${op.toString} ${optionToQuery(rarg)}"
        }
      case _ =>
        s"VALUES (${valuesLists.flatMap(_.toList).map("'" + _.query + "'").mkString(", ")})"
    }

  }

  private def distinctQuery: String = distinctClause match {
    case Nil             => ""
    case List(EmptyNode) => s"DISTINCT "
    case _ =>
      s"DISTINCT ON (${distinctClause.map(_.query).mkString(", ")}) "
  }

  private def targetsQuery: String = {
    targetList.map(_.query).mkString(", ")
  }

  private def fromQuery: String = fromClause match {
    case Nil => ""
    case _   => s" FROM ${fromClause.map(_.query).mkString(", ")}"
  }

  private def whereQuery: String = {
    whereClause.map(clause => s" WHERE ${clause.query}").getOrElse("")
  }

  private def havingQuery: String = {
    havingClause.map(clause => s" HAVING ${clause.query}").getOrElse("")
  }

  private def groupQuery: String = groupClause match {
    case Nil => ""
    case _   => s" GROUP BY ${groupClause.map(_.query).mkString(", ")}"
  }

  private def sortByQuery: String = sortClause match {
    case Nil => ""
    case _   => s" ORDER BY ${sortClause.map(_.query).mkString(", ")}"
  }

  private def intoQuery: String = {
    intoClause.map(clause => s" INTO ${clause.query}").getOrElse("")
  }

  private def limitCountQuery: String = {
    limitCount.map(clause => s" LIMIT ${clause.query}").getOrElse("")
  }
}
