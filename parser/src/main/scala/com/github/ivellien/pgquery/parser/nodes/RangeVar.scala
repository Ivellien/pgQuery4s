package com.github.ivellien.pgquery.parser.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class RangeVar(
    catalogname: Option[String],
    schemaname: Option[String],
    relname: Option[String],
    inh: Boolean,
    relpersistence: Option[String],
    alias: Option[Alias],
    location: Option[Int]
) extends Node {
  override def query: String = relname.getOrElse("")
}

object RangeVar extends NodeDecoder[RangeVar] {
  override implicit protected val vanillaDecoder: Decoder[RangeVar] =
    deriveConfiguredDecoder[RangeVar]
}
