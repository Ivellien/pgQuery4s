package com.github.ivellien.pgquery.parser

class PgQueryWrapper {
//  System.loadLibrary("PgQueryWrapper")
  NativeLoader.loadLibrary("PgQueryWrapper")

  @native def pgQueryParse(query: String): String
}
