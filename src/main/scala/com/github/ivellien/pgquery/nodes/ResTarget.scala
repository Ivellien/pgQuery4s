package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.{ConfiguredJsonCodec, JsonKey}
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class ResTarget(
    name: Option[String],
    indirection: Option[Node],
    @JsonKey("val") value: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String = name match {
    case None => s"${value.map(node => node.query).getOrElse("")}"
    case Some(name) =>
      s"${value.map(node => node.query).getOrElse("")} AS $name"
  }
}
