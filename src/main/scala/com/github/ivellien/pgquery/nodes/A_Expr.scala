package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.A_Expr_Kind
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.{circeConfig, optionToQuery}

@ConfiguredJsonCodec(decodeOnly = true)
case class A_Expr(
    kind: A_Expr_Kind.Value,
    name: List[Node],
    lexpr: Option[Node],
    rexpr: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String = {
    val nameString: String = name.map(x => x.query).headOption.getOrElse("")
    s"${optionToQuery(lexpr)} ${getOperator(nameString)} ${optionToQuery(rexpr)}"
  }

  private def getOperator(name: String): String = {
    kind match {
      case A_Expr_Kind.AexprDistinct    => "IS DISTINCT FROM"
      case A_Expr_Kind.AexprNotDistinct => "IS NOT DISTINCT FROM"
      case A_Expr_Kind.AexprNullif      => "NULLIF"
      case A_Expr_Kind.AexprOf =>
        name match {
          case "="  => "IS OF"
          case "<>" => "IS NOT OF"
          case _    => "" // Log error
        }
      case A_Expr_Kind.AexprIn =>
        name match {
          case "="  => "IN"
          case "<>" => "NOT IN"
          case _    => "" // Log error
        }
      case A_Expr_Kind.AexprLike =>
        name match {
          case "~~"  => "LIKE"
          case "!~~" => "NOT LIKE"
          case _     => "" // Log error
        }
      case A_Expr_Kind.AexprIlike =>
        name match {
          case "~~*"  => "ILIKE"
          case "!~~*" => "NOT ILIKE"
          case _      => "" // Log error
        }
      case A_Expr_Kind.AexprSimilar =>
        name match {
          case "~"  => "SIMILAR"
          case "!~" => "NOT SIMILAR"
          case _    => "" // Log error
        }
      case _ => name
    }
  }
}
