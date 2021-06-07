package com.github.ivellien.pgquery.parser.nodes.values

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.NodeDecoder
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

case class ParamRef(
    number: Int,
    location: Option[Int]
) extends Value {
  override def query: String = s"$$$number"
}

object ParamRef extends NodeDecoder[ParamRef](NodeTag.T_ParamRef) {
  override implicit protected val vanillaDecoder: Decoder[ParamRef] =
    deriveConfiguredDecoder[ParamRef]
}
