package Nodes

import Enums.A_Expr_Kind
import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor

case class A_Expr(
                   kind: A_Expr_Kind.Value,
                   name: Iterable[Node],
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
  def apply(cursor: ACursor): A_Expr = {
    A_Expr(
      kind = A_Expr_Kind(cursor.get[Int]("kind").toOption.head),
      name =  cursor.downField("name").values.head.map(x => Node(x.hcursor)),
      lexpr = cursor.getNodeOption("lexpr"),
      rexpr = cursor.getNodeOption("rexpr"),
      location = cursor.getIntFieldOption("location")
    )
  }
}
