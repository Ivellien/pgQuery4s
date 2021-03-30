package Nodes

import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor

case class ResTarget(
                      name: Option[String],
                      indirection: Option[Node],
                      value: Option[Node],
                      location: Option[Int],
                    ) extends Node {
  override def toQuery(): String = {
    if (value.isEmpty) "" else value.get.toQuery()
  }
}

object ResTarget {
  def apply(cursor: ACursor): ResTarget = {
    ResTarget(
      name = cursor.getStringFieldOption("name"),
      indirection = cursor.getNodeOption("indirection"),
      value = cursor.getNodeOption("val"),
      location = cursor.getIntFieldOption("location")
    )
  }
}
