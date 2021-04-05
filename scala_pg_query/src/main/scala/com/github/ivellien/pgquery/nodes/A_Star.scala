package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though

@ConfiguredJsonCodec(decodeOnly = true)
case class A_Star() extends Node {
  override def query: String = "*"
}