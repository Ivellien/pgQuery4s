package com.github.ivellien.pgquery.nodes
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class TypeName(
    typeOid: Option[Int],
    setof: Option[Boolean],
    pct_type: Option[Boolean],
    typemod: Option[Int],
    location: Option[Int],
    names: List[Node] = List.empty,
    arrayBounds: List[Node] = List.empty,
    typmods: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${names.map(_.query).filter(_ != "pg_catalog").headOption.getOrElse("")}$typmodsQuery"

  private def typmodsQuery: String = typmods match {
    case Nil => ""
    case _   => s"(${typmods.head.query})"
  }
}
