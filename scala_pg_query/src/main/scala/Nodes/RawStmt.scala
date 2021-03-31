package Nodes

import io.circe._
import io.circe.generic.semiauto._

case class RawStmt(
                    stmt: Option[Node],
                    stmt_location: Option[Int],
                    stmt_len: Option[Int],
                  ) extends Node {
  override def toQuery(): String = {
    if (stmt.isEmpty) "" else stmt.get.toQuery()
  }
}

object RawStmt {
  implicit val decoder: Decoder[RawStmt] = deriveDecoder[RawStmt]
}