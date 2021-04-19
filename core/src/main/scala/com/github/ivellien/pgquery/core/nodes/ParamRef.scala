package com.github.ivellien.pgquery.core.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.core.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class ParamRef(
    number: Int,
    location: Option[Int]
) extends Node {
  override def query: String = s"$$$number"
}
