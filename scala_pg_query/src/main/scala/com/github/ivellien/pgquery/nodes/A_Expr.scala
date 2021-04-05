package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.A_Expr_Kind
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig // this must be imported, intellij will see it as unused though

@ConfiguredJsonCodec(decodeOnly = true)
case class A_Expr(
    kind: A_Expr_Kind.Value,
    name: List[Node],
    lexpr: Node = EmptyNode(),
    rexpr: Node = EmptyNode(),
    location: Option[Int]
) extends Node {
  override def query: String = {
    val nameString: String = name.headOption.getOrElse(EmptyNode()).query
    s"${lexpr.query} $nameString ${rexpr.query}"
  }
}
