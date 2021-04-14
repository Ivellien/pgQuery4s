package com.github.ivellien.pgquery.nodes
import com.github.ivellien.pgquery.enums.{AlterTableType, DropBehavior}
import io.circe.generic.extras.{ConfiguredJsonCodec, JsonKey}
import com.github.ivellien.pgquery.nodes.Node.{circeConfig, optionToQuery}

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
