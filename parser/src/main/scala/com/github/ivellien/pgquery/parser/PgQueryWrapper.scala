package com.github.ivellien.pgquery.parser
import ch.jodersky.jni.nativeLoader

@nativeLoader("PgQueryWrapper0")
class PgQueryWrapper {
  @native def pgQueryParse(query: String): String
}
