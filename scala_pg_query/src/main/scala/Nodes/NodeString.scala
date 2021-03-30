package Nodes

import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor

case class NodeString(
                       str: String
                     ) extends Node {
  override def toQuery(): String = str
}
object NodeString {
  def apply(cursor: ACursor): NodeString = {
    NodeString(
      str = cursor.getStringFieldOption("str").head
    )
  }
}