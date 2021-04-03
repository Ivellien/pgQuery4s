package com.github.ivellien.pgquery.nodes

import io.circe._
import io.circe.generic.semiauto._
import com.github.ivellien.pgquery.nodes.Node.circeConfig
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class NodeString(
                       str: String
                     ) extends Node {
  override def query: String = str
}