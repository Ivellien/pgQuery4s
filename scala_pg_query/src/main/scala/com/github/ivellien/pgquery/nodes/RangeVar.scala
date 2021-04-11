package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class RangeVar(
    catalogname: Option[String],
    schemaname: Option[String],
    relname: Option[String],
    inh: Boolean,
    relpersistence: Option[String],
    alias: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String = relname.getOrElse("")
}
