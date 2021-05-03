package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{AlterTableType, DropBehavior}
import io.circe.generic.extras.JsonKey
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class AlterTableCmd(
    subtype: AlterTableType.Value,
    name: Option[String],
    newowner: Option[RoleSpec],
    @JsonKey("def") definition: Option[Node],
    behavior: DropBehavior.Value,
    missing_ok: Option[Boolean]
) extends Node {
  override def query: String =
    s"${subtype.toString}${name.map(" " + _).getOrElse("")}${definition.map(" " + _.query).getOrElse("")}"
}

object AlterTableCmd extends NodeDecoder[AlterTableCmd] {
  override implicit protected val vanillaDecoder: Decoder[AlterTableCmd] =
    deriveConfiguredDecoder[AlterTableCmd]
}
