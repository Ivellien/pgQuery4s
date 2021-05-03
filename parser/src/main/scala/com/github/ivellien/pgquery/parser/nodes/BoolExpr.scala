package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.BoolExprType
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class BoolExpr(
    boolop: BoolExprType.Value,
    args: List[Node],
    location: Option[Int]
) extends Node {
  override def query: String = {
    args.map(_.query).mkString(s" ${boolop.toString} ")
  }
}

object BoolExpr extends NodeDecoder[BoolExpr] {
  override implicit protected val vanillaDecoder: Decoder[BoolExpr] =
    deriveConfiguredDecoder[BoolExpr]
}
