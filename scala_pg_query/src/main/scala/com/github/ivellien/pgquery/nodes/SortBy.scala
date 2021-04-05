package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.{SortByDir, SortByNulls}
import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class SortBy(
    node: Node = EmptyNode(),
    sortby_dir: SortByDir.Value,
    sortby_nulls: SortByNulls.Value,
    useOp: List[Node] = List.empty,
    location: Option[Int]
) extends Node {
  override def query: String =
    s"${node.query}${SortByDir.stringValue(sortby_dir)}"
}
