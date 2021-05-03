package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.NullTestType
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class NullTest(
    arg: Option[Node],
    nulltesttype: NullTestType.Value,
    location: Option[Int],
    argisrow: Option[Boolean]
) extends Node {
  override def query: String =
    s"${optionToQuery(arg)} ${nulltesttype.toString}"
}

object NullTest extends NodeDecoder[NullTest] {
  override implicit protected val vanillaDecoder: Decoder[NullTest] =
    deriveConfiguredDecoder[NullTest]
}
