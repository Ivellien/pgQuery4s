package com.github.ivellien.pgquery.parser.nodes

import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

case class CollateClause(
    arg: Option[Node],
    location: Option[Int],
    collname: List[Node] = List.empty
) extends Node {
  override def query: String = ""
}

object CollateClause extends NodeDecoder[CollateClause] {
  override implicit protected val vanillaDecoder: Decoder[CollateClause] =
    deriveConfiguredDecoder[CollateClause]
}
