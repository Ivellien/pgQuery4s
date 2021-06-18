package com.github.ivellien.pgquery.parser

import com.github.ivellien.pgquery.parser.nodes.{
  Node,
  RawStmt,
  ResTarget,
  SelectStmt
}
import com.github.ivellien.pgquery.native.PgQueryWrapper
import io.circe._

object PgQueryParser {
  final type PgQueryResult[R] = Either[PgQueryError, R]

  val wrapper = new PgQueryWrapper

  def json(query: String): Json =
    parser.parse(wrapper.pgQueryParse(query)).getOrElse(Json.Null)

  def prettify(query: String): String =
    parse(query).map(_.query).getOrElse("")

  def parse(query: String): PgQueryResult[Node] =
    json(query).as[List[Node]] match {
      case Right(node :: _)          => Right(node)
      case Right(Nil)                => Left(EmptyParsingResult)
      case Left(df: DecodingFailure) => Left(FailureWhileParsing(df))
    }

  def parseExpression(expr: String): PgQueryResult[ResTarget] =
    parse("SELECT " + expr) match {
      case Right(RawStmt(_, _, Some(stmt: SelectStmt))) =>
        stmt.targetList match {
          case Nil            => Left(EmptyParsingResult)
          case resTarget :: _ => Right(resTarget)
        }
      case Right(_)    => Left(EmptyParsingResult)
      case Left(error) => Left(error)
    }
}
