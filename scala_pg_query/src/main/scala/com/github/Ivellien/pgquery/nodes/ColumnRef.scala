package com.github.Ivellien.pgquery.nodes

import io.circe._
import io.circe.generic.semiauto._

case class ColumnRef(
                      fields: List[Node],
                      location: Option[Int]
                    ) extends Node {
  override def query: String = fields.map(x => x.query).mkString(",")
}
object ColumnRef {
  implicit val decoder: Decoder[ColumnRef] = deriveDecoder[ColumnRef]
}