package Nodes

import io.circe.ACursor

object CursorSyntax {

  implicit class MyACursor(cursor: ACursor) {
    def getIntFieldOption(name: String): Option[Int] =
      cursor.get[Int](name).toOption

    def getStringFieldOption(name: String): Option[String] =
      cursor.get[String](name).toOption

    def getBooleanField(name: String): Boolean =
      cursor.get[Boolean](name).toOption.head

    def getNodeOption(name: String): Option[Node] = {
      val fieldCursor = cursor.downField(name)
      if (!fieldCursor.failed)
        Some(Node(fieldCursor))
      else
        None
    }

    def getIterableNode(name: String): Iterable[Node] = {
      val fieldCursor = cursor.downField(name)
      if (!fieldCursor.failed)
        fieldCursor.values.head.map(x => Node(x.hcursor))
      else
        Iterable.empty
    }
  }

}
