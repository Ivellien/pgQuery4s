package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{ConstrType, NodeTag}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}

case class Constraint(
    contype: ConstrType.Value,
    conname: Option[String],
    deferrable: Option[Boolean],
    initdeferred: Option[Boolean],
    location: Option[Int],
    is_no_inherit: Option[Boolean],
    raw_expr: Option[Node],
    cooked_expr: Option[Char],
    generated_when: Option[Char],
    indexname: Option[Char],
    indexspace: Option[Char],
    access_method: Option[Char],
    where_clause: Option[Node],
    pktable: Option[RangeVar],
    fk_matchtype: Option[Char],
    fk_upd_action: Option[Char],
    fk_del_action: Option[Char],
    old_pktable_oid: Option[Int],
    skip_validation: Option[Boolean],
    initially_valid: Option[Boolean],
    keys: List[Node] = List.empty,
    exclusions: List[Node] = List.empty,
    options: List[Node] = List.empty,
    fk_attrs: List[Node] = List.empty,
    pk_attrs: List[Node] = List.empty,
    old_conpfeqop: List[Node] = List.empty
) extends Node {
  override def query: String = {
    contype match {
      case ConstrType.ConstrForeign =>
        s"REFERENCES ${optionToQuery(pktable)}(${pk_attrs.headOption.getOrElse(EmptyNode).query})"
      case _ =>
        s"$contype${conname.getOrElse("")}"
    }
  }
}

object Constraint extends NodeDecoder[Constraint](NodeTag.T_Constraint) {
  override implicit protected val vanillaDecoder: Decoder[Constraint] =
    deriveConfiguredDecoder[Constraint]
}
