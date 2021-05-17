package com.github.ivellien.pgquery.parser

import com.github.ivellien.pgquery.parser.nodes.Node
import io.circe.{DecodingFailure, Json}

object PgQueryParser {
  final type PgQueryResult[R] = Either[PgQueryError, R]

  val wrapper = new PgQueryWrapper

  def json(query: String): Json =
    io.circe.parser.parse(wrapper.pgQueryParse(query)).getOrElse(Json.Null)

  def prettify(query: String): String =
    parse(query).map(_.query).getOrElse("")

  def parse(query: String): PgQueryResult[Node] =
    json(query).as[List[Node]] match {
      case Right(node :: _)          => Right(node)
      case Right(Nil)                => Left(EmptyParsingResult)
      case Left(df: DecodingFailure) => Left(FailureWhileParsing(df))
    }
}
