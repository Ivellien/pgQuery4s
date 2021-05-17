package com.github.ivellien.pgquery.parser

class PgQueryWrapper {
  NativeLoader.fromResources("PgQueryWrapper")

  @native def pgQueryParse(query: String): String
}
