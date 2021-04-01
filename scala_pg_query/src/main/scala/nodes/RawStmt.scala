package nodes

import io.circe._
import io.circe.generic.semiauto._

case class RawStmt(
                    stmt: Option[Node],
                    stmtLocation: Option[Int],
                    stmtLen: Option[Int],
                  ) extends Node {
  override def query: String = stmt.getOrElse(EmptyNode()).query
}

object RawStmt {
  implicit val decoder: Decoder[RawStmt] = deriveDecoder[RawStmt]
}