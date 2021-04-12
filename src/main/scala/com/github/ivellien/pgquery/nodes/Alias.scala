package com.github.ivellien.pgquery.nodes
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.{circeConfig, optionToQuery}

@ConfiguredJsonCodec(decodeOnly = true)
case class Alias(
    aliasname: Option[String],
    colnames: List[Node] = List.empty
) extends Node {
  override def query: String = ""
}
