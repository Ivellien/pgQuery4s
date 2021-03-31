package Nodes

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
  override def toQuery(): String = if (relname.isEmpty) "" else relname.get
}

object RangeVar {
  implicit val decoder: Decoder[RangeVar] = deriveDecoder[RangeVar]
}
