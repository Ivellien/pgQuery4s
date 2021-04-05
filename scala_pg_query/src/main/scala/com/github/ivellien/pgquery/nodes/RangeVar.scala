package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though

@ConfiguredJsonCodec(decodeOnly = true)
case class RangeVar(
    catalogname: Option[String],
    schemaname: Option[String],
    relname: Option[String],
    inh: Boolean,
    relpersistence: Option[String],
    alias: Node = EmptyNode(),
    location: Option[Int]
) extends Node {
  override def query: String = relname.getOrElse("")
}
