package com.github.ivellien.pgquery.enums

import io.circe.{Decoder, DecodingFailure}

object SortByNulls extends Enumeration {
  val SortByNullsDefault, SortByNullsFirst, SortByNullsLast = Value

  private lazy val byId: Map[Int, SortByNulls.Value] =
    values.map(v => v.id -> v).toMap

  implicit val decoder: Decoder[SortByNulls.Value] = c =>
    c.as[Int]
      .flatMap(i =>
        byId.get(i).toRight(DecodingFailure(s"No such ID: ${i}", c.history))
      )

}
