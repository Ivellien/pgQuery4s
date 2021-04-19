package com.github.ivellien.pgquery.core

class PgQueryWrapper {
  System.loadLibrary("PgQueryWrapper")

  @native def pgQueryParse(query: String): String
}
