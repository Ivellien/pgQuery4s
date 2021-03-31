package Nodes

import io.circe._
import io.circe.generic.semiauto._

case class NodeString(
                       str: String
                     ) extends Node {
  override def toQuery(): String = str
}
object NodeString {
  implicit val decoder: Decoder[NodeString] = deriveDecoder[NodeString]
}