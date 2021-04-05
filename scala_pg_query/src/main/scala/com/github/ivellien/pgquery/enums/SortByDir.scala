package com.github.ivellien.pgquery.enums

import com.github.ivellien.pgquery.enums.BoolExprType.{AndExpr, NotExpr, OrExpr}
import com.typesafe.scalalogging.Logger
import io.circe.{Decoder, DecodingFailure}

object SortByDir extends Enumeration {
  val SortByDefault, SortByAsc, SortByDesc, SortByUsing = Value

  private lazy val byId: Map[Int, SortByDir.Value] =
    values.map(v => v.id -> v).toMap

  def stringValue(sortByDir: SortByDir.Value): String = {
    sortByDir match {
      case SortByDefault => ""
      case SortByAsc     => " ASC"
      case SortByDesc    => " DESC"
      case _ =>
        Logger("Error").error(s"Invalid SortByDir type. $sortByDir")
        ""
    }
  }

  implicit val decoder: Decoder[SortByDir.Value] = c =>
    c.as[Int]
      .flatMap(i =>
        byId.get(i).toRight(DecodingFailure(s"No such ID: ${i}", c.history))
      )

}
