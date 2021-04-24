package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{AlterTableType, DropBehavior}
import io.circe.generic.extras.{ConfiguredJsonCodec, JsonKey}
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class AlterTableCmd(
    subtype: AlterTableType.Value,
    name: Option[String],
    newowner: Option[Node],
    @JsonKey("def") definition: Option[Node],
    behavior: DropBehavior.Value,
    missing_ok: Option[Boolean]
) extends Node {
  override def query: String =
    s"${subtype.toString}${name.map(" " + _).getOrElse("")}${definition.map(" " + _.query).getOrElse("")}"
}
