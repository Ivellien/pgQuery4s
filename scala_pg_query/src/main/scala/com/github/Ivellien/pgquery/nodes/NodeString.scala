package com.github.Ivellien.pgquery.nodes

import io.circe._
import io.circe.generic.semiauto._

case class NodeString(
                       str: String
                     ) extends Node {
  override def query: String = str
}
object NodeString {
  implicit val decoder: Decoder[NodeString] = deriveDecoder[NodeString]
}