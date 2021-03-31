package Nodes

import io.circe._
import io.circe.generic.semiauto._

case class ColumnRef(
                      fields: List[Node],
                      location: Option[Int]
                    ) extends Node {
  override def toQuery(): String = {
    fields.map(x => x.toQuery()).mkString(",")
  }
}
object ColumnRef {
  implicit val decoder: Decoder[ColumnRef] = deriveDecoder[ColumnRef]
}