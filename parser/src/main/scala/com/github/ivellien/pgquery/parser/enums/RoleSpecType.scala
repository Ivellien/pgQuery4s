package com.github.ivellien.pgquery.parser.enums

object RoleSpecType extends Enumeration with EnumerationDecoder {
  val RoleSpecCstring, RoleSpecCurrentUser, RoleSpecSessionUser,
      RoleSpecPublic = Value
}
