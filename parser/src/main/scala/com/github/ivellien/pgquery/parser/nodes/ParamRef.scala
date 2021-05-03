package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class ParamRef(
    number: Int,
    location: Option[Int]
) extends Node {
  override def query: String = s"$$$number"
}

object ParamRef extends NodeDecoder[ParamRef] {
  override implicit protected val vanillaDecoder: Decoder[ParamRef] =
    deriveConfiguredDecoder[ParamRef]
}
