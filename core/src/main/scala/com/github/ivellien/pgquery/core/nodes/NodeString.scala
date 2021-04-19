package com.github.ivellien.pgquery.core.nodes

import com.github.ivellien.pgquery.core.nodes.Node.circeConfig
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class NodeString(
    str: String
) extends Node {
  override def query: String = str
}
