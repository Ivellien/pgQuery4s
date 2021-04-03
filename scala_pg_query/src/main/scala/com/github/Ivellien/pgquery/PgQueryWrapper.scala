package com.github.Ivellien.pgquery

class PgQueryWrapper {
  System.loadLibrary("PgQueryWrapper")

  @native def pgQueryParse(query: String): String
}
