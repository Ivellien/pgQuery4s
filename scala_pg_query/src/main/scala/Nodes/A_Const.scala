package Nodes

import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor

case class A_Const(
                    value: Option[Node],
                    location: Option[Int]
                  ) extends Node {
  override def toQuery(): String = {
    if (value.isEmpty) "" else value.get.toQuery()
  }
}
object A_Const {
  def apply(cursor: ACursor): A_Const = {
    A_Const(
      value = cursor.getNodeOption("val"),
      location = cursor.getIntFieldOption("location")
    )
  }
}

