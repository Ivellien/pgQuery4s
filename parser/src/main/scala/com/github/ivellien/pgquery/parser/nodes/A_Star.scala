package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case object A_Star extends Node {
  override def query: String = "*"

  implicit val decoder: Decoder[A_Star.type] =
    deriveConfiguredDecoder[A_Star.type]
}
