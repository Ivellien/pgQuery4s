package nodes // this still needs to have proper package prefix like com.github.yourusername.

import nodes.Node.circeConfig // this must be imported, intellij will see it as unused though
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class RawStmt(stmtLocation: Option[Int],
                   stmtLen: Option[Int],
                   stmt: Node = EmptyNode(), // with the default params enabled in the circe config you can do this
                  ) extends Node {
  override def query: String = stmt.query // than this becomes much simpler
}