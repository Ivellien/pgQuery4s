package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class FuncCall(
    aggFilter: Option[Node],
    aggWithinGroup: Option[Boolean],
    aggStar: Option[Boolean],
    aggDistinct: Option[Boolean],
    funcVariadic: Option[Boolean],
    location: Option[Int],
    funcname: List[Node] = List.empty,
    args: List[Node] = List.empty,
    aggOrder: List[Node] = List.empty
) extends Node {
  override def query: String = funcname match {
    case Nil => ""
    case _ =>
      s"${funcname.head.query}(${args.map(_.query).mkString(", ")})"
  }
}

object FuncCall extends NodeDecoder[FuncCall] {
  override implicit protected val vanillaDecoder: Decoder[FuncCall] =
    deriveConfiguredDecoder[FuncCall]
}
