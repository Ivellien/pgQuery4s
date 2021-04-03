package com.github.Ivellien.pgquery.nodes

import io.circe._
import io.circe.generic.semiauto._

case class RangeVar(
                     catalogname: Option[String],
                     schemaname: Option[String],
                     relname: Option[String],
                     inh: Boolean,
                     relpersistence: Option[String],
                     alias: Option[Node],
                     location: Option[Int],
                   ) extends Node {
  override def query: String = relname.getOrElse("")
}

object RangeVar {
  implicit val decoder: Decoder[RangeVar] = deriveDecoder[RangeVar]
}
