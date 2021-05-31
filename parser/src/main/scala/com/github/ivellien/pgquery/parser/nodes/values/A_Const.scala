package com.github.ivellien.pgquery.parser.nodes.values

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import com.github.ivellien.pgquery.parser.nodes.NodeDecoder
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import io.circe.generic.extras.JsonKey

case class A_Const(
    @JsonKey("val") value: Option[Value],
    location: Option[Int]
) extends Value {
  override def query: String = optionToQuery(value)
}

object A_Const extends NodeDecoder[A_Const](NodeTag.T_A_Const) {
  override implicit protected val vanillaDecoder: Decoder[A_Const] =
    deriveConfiguredDecoder[A_Const]
}
