package Nodes

import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor


case class NodeInteger(
                        ival: Int
                      ) extends Node {
  override def toQuery(): String = ival.toString
}
object NodeInteger {
  def apply(cursor: ACursor): NodeInteger = {
    NodeInteger(
      ival = cursor.getIntFieldOption("ival").head
    )
  }
}