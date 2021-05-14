package com.github.ivellien.pgquery.parser

import com.github.ivellien.pgquery.parser.nodes.Node
import io.circe.{Decoder, DecodingFailure, Json}
import io.circe.parser.parse

object PgQueryParser {
  final type PgQueryResult[R] = Either[PgQueryError, R]

  sealed trait PgQueryError
  case object EmptyParsingResult extends PgQueryError
  case class FailureWhileParsing(decodingFailure: DecodingFailure)
      extends PgQueryError

  val wrapper = new PgQueryWrapper

  def json(query: String): Json =
    parse(wrapper.pgQueryParse(query)).getOrElse(Json.Null)

  def prettify(query: String): String =
    parseTree(query).map(_.query).getOrElse("")

  def parseTree(query: String): PgQueryResult[Node] =
    json(query).as[List[Node]] match {
      case Right(node :: _)          => Right(node)
      case Right(Nil)                => Left(EmptyParsingResult)
      case Left(df: DecodingFailure) => Left(FailureWhileParsing(df))
    }
}
