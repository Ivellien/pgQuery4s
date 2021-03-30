package Nodes

import Nodes.CursorSyntax.MyACursor
import io.circe.ACursor

case class ColumnRef(
                      fields: Iterable[Node],
                      location: Option[Int]
                    ) extends Node {
  override def toQuery(): String = {
    fields.map(x => x.toQuery()).mkString(",")
  }
}
object ColumnRef {
  def apply(cursor: ACursor): ColumnRef = {
    ColumnRef(
      fields = cursor.downField("fields").values.head.map(x => Node(x.hcursor)),
      location = cursor.getIntFieldOption("location")
    )
  }
}