package com.github.ivellien.pgquery.parser.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class ParamRef(
    number: Int,
    location: Option[Int]
) extends Node {
  override def query: String = s"$$$number"
}
