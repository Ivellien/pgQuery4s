package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though
import io.circe.generic.extras.{ConfiguredJsonCodec, JsonKey}

@ConfiguredJsonCodec(decodeOnly = true)
case class A_Const(
    @JsonKey("val") value: Node = EmptyNode(),
    location: Option[Int]
) extends Node {
  override def query: String = value.query
}
