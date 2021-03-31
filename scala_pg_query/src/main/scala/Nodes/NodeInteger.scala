package Nodes

import io.circe._
import io.circe.generic.semiauto._

case class NodeInteger(
                        ival: Int
                      ) extends Node {
  override def toQuery(): String = ival.toString
}
object NodeInteger {
  implicit val decoder: Decoder[NodeInteger] = deriveDecoder[NodeInteger]
}