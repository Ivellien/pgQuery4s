package com.github.Ivellien.pgquery.nodes

import com.github.Ivellien.pgquery.enums.A_Expr_Kind
import io.circe._
import io.circe.generic.semiauto._

case class A_Expr(
                   kind: A_Expr_Kind.Value,
                   name: List[Node],
                   lexpr: Option[Node],
                   rexpr: Option[Node],
                   location: Option[Int],
                 ) extends Node {
  override def query: String = {
    val lexprString: String = lexpr.getOrElse(EmptyNode()).query
    val rexprString: String = rexpr.getOrElse(EmptyNode()).query
    val nameString: String = name.headOption.getOrElse(EmptyNode()).query
    lexprString + " " + nameString + " " + rexprString
  }
}
object A_Expr {
  implicit val decoder: Decoder[A_Expr] = deriveDecoder[A_Expr]
}
