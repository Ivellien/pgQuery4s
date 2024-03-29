package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{A_Expr_Kind, NodeTag}
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import com.typesafe.scalalogging.LazyLogging
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class A_Expr(
    kind: A_Expr_Kind.Value,
    name: List[Node],
    lexpr: Option[Node],
    rexpr: Option[Node],
    location: Option[Int]
) extends Node
    with LazyLogging {
  override def query: String = {
    val nameString: String = name.map(_.query).headOption.getOrElse("")
    s"${optionToQuery(lexpr)} ${getOperator(nameString)} ${optionToQuery(rexpr)}"
  }

  private def getOperator(name: String): String = {
    (kind, name) match {
      case (A_Expr_Kind.AexprDistinct, _)    => "IS DISTINCT FROM"
      case (A_Expr_Kind.AexprNotDistinct, _) => "IS NOT DISTINCT FROM"
      case (A_Expr_Kind.AexprNullif, _)      => "NULLIF"
      case (A_Expr_Kind.AexprOf, "=")        => "IS OF"
      case (A_Expr_Kind.AexprOf, "<>")       => "IS NOT OF"
      case (A_Expr_Kind.AexprOf, _) =>
        logger.error(s"Unsupported name for $kind")
        ""
      case (A_Expr_Kind.AexprIn, "=")  => "IN"
      case (A_Expr_Kind.AexprIn, "<>") => "NOT IN"
      case (A_Expr_Kind.AexprIn, _) =>
        logger.error(s"Unsupported name for $kind")
        ""
      case (A_Expr_Kind.AexprLike, "~~")  => "LIKE"
      case (A_Expr_Kind.AexprLike, "!~~") => "NOT LIKE"
      case (A_Expr_Kind.AexprLike, _) =>
        logger.error(s"Unsupported name for $kind")
        ""
      case (A_Expr_Kind.AexprIlike, "~~*")  => "ILIKE"
      case (A_Expr_Kind.AexprIlike, "!~~*") => "NOT ILIKE"
      case (A_Expr_Kind.AexprIlike, _) =>
        logger.error(s"Unsupported name for $kind")
        ""
      case (A_Expr_Kind.AexprSimilar, "~")  => "SIMILAR"
      case (A_Expr_Kind.AexprSimilar, "!~") => "NOT SIMILAR"
      case (A_Expr_Kind.AexprSimilar, _) =>
        logger.error(s"Unsupported name for $kind")
        ""
      case (_, _) => name
    }
  }
}

object A_Expr extends NodeDecoder[A_Expr](NodeTag.T_A_Expr) {
  override implicit protected val vanillaDecoder: Decoder[A_Expr] =
    deriveConfiguredDecoder[A_Expr]
}
