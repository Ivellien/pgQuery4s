package Nodes

import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor

case class RangeVar(
                     catalogname: Option[String],
                     schemaname: Option[String],
                     relname: Option[String],
                     inh: Boolean,
                     relpersistence: Option[String],
                     alias: Option[Node],
                     location: Option[Int],
                   ) extends Node {
  override def toQuery(): String = if (relname.isEmpty) "" else relname.get
}

object RangeVar {
  def apply(cursor: ACursor): RangeVar = {
    RangeVar(
      catalogname = cursor.getStringFieldOption("catalogname"),
      schemaname = cursor.getStringFieldOption("schemaname"),
      relname = cursor.getStringFieldOption("relname"),
      inh = cursor.getBooleanField("inh"),
      relpersistence = cursor.getStringFieldOption("relpersistence"),
      alias = None,
      location = cursor.getIntFieldOption("location")
    )
  }
}
