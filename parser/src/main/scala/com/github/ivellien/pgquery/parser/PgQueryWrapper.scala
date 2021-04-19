package com.github.ivellien.pgquery.parser

class PgQueryWrapper {
  System.loadLibrary("PgQueryWrapper")

  @native def pgQueryParse(query: String): String
}
