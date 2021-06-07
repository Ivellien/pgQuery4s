package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.NodeTag
import io.circe.generic.extras.JsonKey
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

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

object ResTarget extends NodeDecoder[ResTarget](NodeTag.T_ResTarget) {
  override implicit protected val vanillaDecoder: Decoder[ResTarget] =
    deriveConfiguredDecoder[ResTarget]
}
