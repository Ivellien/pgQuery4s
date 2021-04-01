package nodes

import io.circe._
import io.circe.generic.semiauto._

case class NodeInteger(
                        ival: Int
                      ) extends Node {
  override def query: String = ival.toString
}
object NodeInteger {
  implicit val decoder: Decoder[NodeInteger] = deriveDecoder[NodeInteger]
}