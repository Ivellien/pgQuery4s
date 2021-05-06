package com.github.ivellien.pgquery.parser.nodes.values

import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

case object A_Star extends Value {
  override def query: String = "*"

  implicit val decoder: Decoder[A_Star.type] =
    deriveConfiguredDecoder[A_Star.type]
}
