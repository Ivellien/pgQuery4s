package com.github.ivellien.pgquery.core.nodes

import com.github.ivellien.pgquery.core.nodes.Node
import com.github.ivellien.pgquery.core.nodes.Node.{circeConfig, optionToQuery}
import io.circe.generic.extras.{ConfiguredJsonCodec, JsonKey}

@ConfiguredJsonCodec(decodeOnly = true)
case class A_Const(
    @JsonKey("val") value: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String = optionToQuery(value)
}
