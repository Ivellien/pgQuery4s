package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import com.github.ivellien.pgquery.parser.nodes.values.Value
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class TypeName(
    typeOid: Option[Int],
    setof: Option[Boolean],
    pct_type: Option[Boolean],
    typemod: Option[Int],
    location: Option[Int],
    names: List[Value] = List.empty,
    arrayBounds: List[Node] = List.empty,
    typmods: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${names.map(_.query).find(_ != "pg_catalog").getOrElse("")}$typmodsQuery"

  private def typmodsQuery: String = typmods match {
    case Nil => ""
    case _   => s"(${typmods.head.query})"
  }
}

object TypeName extends NodeDecoder[TypeName] {
  override implicit protected val vanillaDecoder: Decoder[TypeName] =
    deriveConfiguredDecoder[TypeName]
}
