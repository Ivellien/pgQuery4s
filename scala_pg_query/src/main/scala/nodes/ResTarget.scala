package nodes

import io.circe._
import io.circe.generic.extras.JsonKey
import io.circe.generic.extras.semiauto._
// marked as always used in intellij, otherwise the optimizer will get rid of it
import nodes.Node.circeConfig

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
