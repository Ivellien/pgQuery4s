package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though

@ConfiguredJsonCodec(decodeOnly = true)
case class SelectStmt(
    distinctClause: Node = EmptyNode(),
    intoClause: Node = EmptyNode(),
    targetList: List[Node],
    fromClause: List[Node],
    whereClause: Node = EmptyNode(),
    groupClause: Node = EmptyNode(),
    havingClause: Node = EmptyNode(),
    op: Option[Int]
) extends Node {
  override def query: String = {
    val targets: String = targetList.map(x => x.query).mkString(",")
    val from: String = fromClause.map(x => x.query).mkString(",")
    s"SELECT $targets FROM $from WHERE ${whereClause.query}"
  }
}
