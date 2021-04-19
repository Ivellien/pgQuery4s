package com.github.ivellien.pgquery.core.nodes

import io.circe.generic.extras.{ConfiguredJsonCodec, JsonKey}
import com.github.ivellien.pgquery.core.nodes.Node.{circeConfig, optionToQuery}

@ConfiguredJsonCodec(decodeOnly = true)
case class ResTarget(
    name: Option[String],
    indirection: Option[Node],
    @JsonKey("val") value: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String = (name, value) match {
    case (None, None)              => s""
    case (None, Some(value))       => s"${value.query}"
    case (Some(name), None)        => s"$name"
    case (Some(name), Some(value)) => s"${value.query} AS $name"
  }
}
