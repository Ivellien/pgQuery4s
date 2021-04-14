package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.{DropBehavior, ObjectType}
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class DropStmt(
    removeType: ObjectType.Value,
    behavior: DropBehavior.Value,
    concurrent: Option[Boolean],
    missing_ok: Option[Boolean],
    objects: List[List[Node]] = List.empty
) extends Node {
  override def query: String =
    s"DROP TABLE $missingOkQuery${objects.flatMap(_.toList).map(_.query).mkString(", ")}"

  private def missingOkQuery: String = missing_ok.getOrElse(false) match {
    case true => "IF EXISTS "
    case _    => ""
  }
}
