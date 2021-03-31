package Nodes

import Enums.A_Expr_Kind
import io.circe._
import io.circe.generic.semiauto._

case class A_Expr(
                   kind: A_Expr_Kind.Value,
                   name: List[Node],
                   lexpr: Option[Node],
                   rexpr: Option[Node],
                   location: Option[Int],
                 ) extends Node {
  override def toQuery(): String = {
    val lexprString: String = if (lexpr.isEmpty) "" else lexpr.get.toQuery()
    val rexprString: String = if (rexpr.isEmpty) "" else rexpr.get.toQuery()
    val nameString: String = if (name.isEmpty) "" else name.head.toQuery()
    lexprString + " " + nameString + " " + rexprString
  }
}
object A_Expr {
  implicit val decoder: Decoder[A_Expr] = deriveDecoder[A_Expr]
}
