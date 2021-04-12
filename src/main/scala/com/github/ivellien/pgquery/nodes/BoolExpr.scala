package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.BoolExprType
import com.github.ivellien.pgquery.nodes.Node.circeConfig
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class BoolExpr(
    boolop: BoolExprType.Value,
    args: List[Node],
    location: Option[Int]
) extends Node {
  override def query: String = {
    args.map(x => x.query).mkString(s" ${boolop.toString} ")
  }
}
