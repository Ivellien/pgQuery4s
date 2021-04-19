package com.github.ivellien.pgquery.parser.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
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
