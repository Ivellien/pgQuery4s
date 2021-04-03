package com.github.Ivellien.pgquery.nodes

import io.circe._
import io.circe.generic.extras.semiauto._
import com.github.Ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though
import io.circe.generic.extras.JsonKey

case class A_Const(
                    @JsonKey("val") value: Option[Node],
                    location: Option[Int]
                  ) extends Node {
  override def query: String = value.getOrElse(EmptyNode()).query
}
object A_Const {
  implicit val decoder: Decoder[A_Const] = deriveConfiguredDecoder[A_Const]
}

