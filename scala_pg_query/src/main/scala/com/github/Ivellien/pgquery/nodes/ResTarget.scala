package com.github.Ivellien.pgquery.nodes

import io.circe._
import io.circe.generic.extras.JsonKey
import io.circe.generic.extras.semiauto._
import com.github.Ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though

case class ResTarget(
                      name: Option[String],
                      indirection: Option[Node],
                      @JsonKey("val") value: Option[Node],
                      location: Option[Int],
                    ) extends Node {
  override def query: String = value.getOrElse(EmptyNode()).query
}

object ResTarget {
  implicit val decoder: Decoder[ResTarget] = deriveConfiguredDecoder[ResTarget]
}
