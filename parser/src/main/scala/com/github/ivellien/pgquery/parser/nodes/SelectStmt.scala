package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{NodeTag, SetOperation}
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import com.github.ivellien.pgquery.parser.nodes.values.Value
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class SelectStmt(
    intoClause: Option[IntoClause],
    whereClause: Option[Node],
    havingClause: Option[Node],
    withClause: Option[Node], // TODO WithClause
    limitOffset: Option[Node],
    limitCount: Option[Node],
    op: SetOperation.Value,
    larg: Option[Node],
    rarg: Option[Node],
    valuesLists: List[List[Value]] = List.empty,
    groupClause: List[Node] = List.empty,
    distinctClause: List[Node] = List.empty,
    targetList: List[ResTarget] = List.empty,
    fromClause: List[Node] = List.empty,
    windowClause: List[Node] = List.empty,
    sortClause: List[Node] = List.empty,
    lockingClause: List[Node] = List.empty
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

object SelectStmt extends NodeDecoder[SelectStmt](NodeTag.T_SelectStmt) {
  override implicit protected val vanillaDecoder: Decoder[SelectStmt] =
    deriveConfiguredDecoder[SelectStmt]
}
