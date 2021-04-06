package com.github.ivellien.pgquery.enums

import com.github.ivellien.pgquery.enums.A_Expr_Kind.values
import io.circe.{Decoder, DecodingFailure}

trait EnumerationDecoder {
  this: Enumeration =>

  private lazy val byId: Map[Int, this.Value] =
  values.map(v => v.id -> v).toMap

  implicit val decoder: Decoder[this.Value] = c =>
    c.as[Int]
      .flatMap(i =>
        byId.get(i).toRight(DecodingFailure(s"No such ID: ${i}", c.history))
      )
}
