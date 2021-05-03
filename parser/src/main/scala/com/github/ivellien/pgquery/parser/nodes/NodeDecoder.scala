package com.github.ivellien.pgquery.parser.nodes

import io.circe.{ACursor, Decoder, DecodingFailure, HCursor}

trait NodeDecoder[N] {
  implicit protected val vanillaDecoder: Decoder[N]

  implicit val decoder: Decoder[N] = (c: HCursor) => {
    val keys = c.keys.map(_.toSeq)
    keys match {
      case Some(Seq(key)) =>
        val value: ACursor = c.downField(key)
        vanillaDecoder.tryDecode(value)
      case _ =>
        Left(DecodingFailure(s"Tag not found in ${keys}", c.history))
    }
  }
}
