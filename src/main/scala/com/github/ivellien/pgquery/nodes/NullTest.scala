package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.NullTestType
import com.github.ivellien.pgquery.nodes.Node.circeConfig
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class NullTest(
    arg: Option[Node],
    nulltesttype: NullTestType.Value,
    location: Option[Int],
    argisrow: Option[Boolean]
) extends Node {
  override def query: String =
    s"${arg.map(arg => arg.query).getOrElse("")} ${nulltesttype.toString}"
}
