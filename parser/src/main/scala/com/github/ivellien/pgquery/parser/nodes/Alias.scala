package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import com.github.ivellien.pgquery.parser.nodes.values.NodeString
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class Alias(
    aliasname: Option[String],
    colnames: List[NodeString] = List.empty
) extends Node {
  override def query: String = ""
}

object Alias extends NodeDecoder[Alias] {
  override implicit protected val vanillaDecoder: Decoder[Alias] =
    deriveConfiguredDecoder[Alias]
}
