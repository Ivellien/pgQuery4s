package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.A_Expr_Kind
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class A_Expr(
    kind: A_Expr_Kind.Value,
    name: List[Node],
    lexpr: Option[Node],
    rexpr: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String = {
    val nameString: String = name.map(x => x.query).headOption.getOrElse("")
    s"${lexpr.map(node => node.query).getOrElse("")} $nameString ${rexpr.map(node => node.query).getOrElse("")}"
  }
}
