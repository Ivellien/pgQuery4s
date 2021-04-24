package com.github.ivellien.pgquery.parser.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}

@ConfiguredJsonCodec(decodeOnly = true)
case class Alias(
    aliasname: Option[String],
    colnames: List[Node] = List.empty
) extends Node {
  override def query: String = ""
}
