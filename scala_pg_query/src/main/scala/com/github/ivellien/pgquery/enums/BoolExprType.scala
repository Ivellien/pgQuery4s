package com.github.ivellien.pgquery.enums

import com.typesafe.scalalogging.Logger
import io.circe.{Decoder, DecodingFailure}

object BoolExprType extends Enumeration {
  val AndExpr, OrExpr, NotExpr = Value

  private lazy val byId: Map[Int, BoolExprType.Value] =
    values.map(v => v.id -> v).toMap

  def stringValue(boolExprType: BoolExprType.Value): String = {
    boolExprType match {
      case AndExpr => "and"
      case OrExpr  => "or"
      case NotExpr => "not"
      case _ =>
        Logger("Error").error("Invalid BoolExprType.")
        ""
    }
  }

  implicit val decoder: Decoder[BoolExprType.Value] = c =>
    c.as[Int]
      .flatMap(i =>
        byId.get(i).toRight(DecodingFailure(s"No such ID: ${i}", c.history))
      )

}
