package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.RoleSpecType
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

case class RoleSpec(
    roletype: RoleSpecType.Value,
    rolename: Option[String],
    location: Option[Int]
) extends Node {
  override def query: String = ""
}

object RoleSpec extends NodeDecoder[RoleSpec] {
  override implicit protected val vanillaDecoder: Decoder[RoleSpec] =
    deriveConfiguredDecoder[RoleSpec]
}
