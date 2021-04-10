package com.github.ivellien.pgquery.enums

import com.github.ivellien.pgquery.enums.BoolExprType.{AndExpr, NotExpr, OrExpr}
import com.typesafe.scalalogging.Logger
import io.circe.{Decoder, DecodingFailure}

object SortByDir extends Enumeration with EnumerationDecoder {
  val SortByDefault = Value("")
  val SortByAsc = Value(" ASC")
  val SortByDesc = Value(" DESC")
  val SortByUsing = Value(" USING")
}
