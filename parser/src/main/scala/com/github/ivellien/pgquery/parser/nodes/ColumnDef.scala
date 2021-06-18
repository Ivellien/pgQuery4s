package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.NodeTag
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class ColumnDef(
    colname: Option[String],
    typeName: Option[TypeName], // TypeName
    inhcount: Option[Int],
    is_local: Option[Boolean],
    is_not_null: Option[Boolean],
    is_from_type: Option[Boolean],
    is_from_parent: Option[Boolean],
    storage: Option[Char],
    raw_default: Option[Node],
    cooked_default: Option[Node],
    identity: Option[Char],
    identitySequence: Option[RangeVar], // RangeVar
    collClause: Option[CollateClause], // CollateClause
    collOid: Option[Int],
    location: Option[Int],
    constraints: List[Constraint] = List.empty,
    fdwoptions: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${colname.getOrElse("")}${typeName.map(" " + _.query).getOrElse("")}${constraints.map(" " + _.query).mkString}"
}

object ColumnDef extends NodeDecoder[ColumnDef](NodeTag.T_ColumnDef) {
  override implicit protected val vanillaDecoder: Decoder[ColumnDef] =
    deriveConfiguredDecoder[ColumnDef]
}
