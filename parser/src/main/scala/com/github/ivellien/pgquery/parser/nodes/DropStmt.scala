package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{
  DropBehavior,
  NodeTag,
  ObjectType
}
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

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

object DropStmt extends NodeDecoder[DropStmt](NodeTag.T_DropStmt) {
  override implicit protected val vanillaDecoder: Decoder[DropStmt] =
    deriveConfiguredDecoder[DropStmt]
}
