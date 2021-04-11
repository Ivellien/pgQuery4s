package com.github.ivellien.pgquery

class PgQueryWrapper {
  System.loadLibrary("PgQueryWrapper")

  @native def pgQueryParse(query: String): String
}
