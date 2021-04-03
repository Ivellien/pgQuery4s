package com.github.ivellien.pgquery.nodes

import io.circe._
import io.circe.generic.extras.{ConfiguredJsonCodec, JsonKey}
import io.circe.generic.extras.semiauto._
import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though

@ConfiguredJsonCodec(decodeOnly = true)
case class ResTarget(
                      name: Option[String],
                      indirection: Node = EmptyNode(),
                      @JsonKey("val") value: Node = EmptyNode(),
                      location: Option[Int],
                    ) extends Node {
  override def query: String = value.query
}