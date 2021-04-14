package com.github.ivellien.pgquery.nodes
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.{circeConfig, optionToQuery}

@ConfiguredJsonCodec(decodeOnly = true)
case class ColumnDef(
    colname: Option[String],
    typeName: Option[Node], // TypeName
    inhcount: Option[Int],
    is_local: Option[Boolean],
    is_not_null: Option[Boolean],
    is_from_type: Option[Boolean],
    is_from_parent: Option[Boolean],
    storage: Option[Char],
    raw_default: Option[Node],
    cooked_default: Option[Node],
    identity: Option[Char],
    identitySequence: Option[Node], // RangeVar
    collClause: Option[Node], // CollateClause
    collOid: Option[Int],
    location: Option[Int],
    constraints: List[Node] = List.empty,
    fdwoptions: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${colname.getOrElse("")}${typeName.map(" " + _.query).getOrElse("")}"
}
