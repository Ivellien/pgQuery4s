package com.github.ivellien.pgquery

import com.github.ivellien.pgquery.nodes.Node
import io.circe.Json
import io.circe.parser.parse

class PgQueryParser(query: String) {
  val originalQuery: String = query
  val wrapper = new PgQueryWrapper
  val parseTree = json.as[List[Node]]

  def json: Json = parse(wrapper.pgQueryParse(query)).getOrElse(Json.Null)

  def prettify: String = parseTree.map(_.head.query).getOrElse("")
}
