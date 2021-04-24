package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class NodeString(
    str: String
) extends Node {
  override def query: String = str
}
