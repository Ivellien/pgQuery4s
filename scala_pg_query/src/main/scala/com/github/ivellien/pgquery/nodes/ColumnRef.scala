package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class ColumnRef(
                      fields: List[Node],
                      location: Option[Int]
                    ) extends Node {
  override def query: String = fields.map(x => x.query).mkString(",")
}