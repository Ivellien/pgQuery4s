package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case object A_Star extends Node {
  override def query: String = "*"

  implicit val decoder: Decoder[A_Star.type] =
    deriveConfiguredDecoder[A_Star.type]
}
