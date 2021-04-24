package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.BoolExprType
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class BoolExpr(
    boolop: BoolExprType.Value,
    args: List[Node],
    location: Option[Int]
) extends Node {
  override def query: String = {
    args.map(_.query).mkString(s" ${boolop.toString} ")
  }
}
