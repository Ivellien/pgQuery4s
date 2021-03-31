package Nodes

import io.circe._
import io.circe.generic.extras.semiauto._
// marked as always used in intellij, otherwise the optimizer will get rid of it
import Nodes.Node.circeConfig
import io.circe.generic.extras.JsonKey

case class A_Const(
                    @JsonKey("val") value: Option[Node],
                    location: Option[Int]
                  ) extends Node {
  override def toQuery(): String = {
    if (value.isEmpty) "" else value.get.toQuery()
  }
}
object A_Const {
  implicit val decoder: Decoder[A_Const] = deriveConfiguredDecoder[A_Const]
}

