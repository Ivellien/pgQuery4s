package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.nodes.Node
import io.circe.{Decoder, Json}
import io.circe.parser.parse

object PgQueryParser {
  val wrapper = new PgQueryWrapper

  def json(query: String): Json =
    parse(wrapper.pgQueryParse(query)).getOrElse(Json.Null)

  def prettify(query: String): String =
    parseTree(query).map(_.head.query).getOrElse("")

  def parseTree(query: String): Decoder.Result[List[Node]] =
    json(query).as[List[Node]]
}
