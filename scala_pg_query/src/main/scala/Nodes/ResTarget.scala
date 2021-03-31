package Nodes

import io.circe._
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.JsonKey
// marked as always used in intellij, otherwise the optimizer will get rid of it
import Nodes.Node.circeConfig


case class ResTarget(
                      name: Option[String],
                      indirection: Option[Node],
                      @JsonKey("val") value: Option[Node],
                      location: Option[Int],
                    ) extends Node {
  override def toQuery(): String = {
    if (value.isEmpty) "" else value.get.toQuery()
  }
}

object ResTarget {
  implicit val decoder: Decoder[ResTarget] = deriveConfiguredDecoder[ResTarget]
}
