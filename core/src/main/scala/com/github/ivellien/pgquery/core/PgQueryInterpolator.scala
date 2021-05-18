package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.macros.MacrosImpl

import scala.language.experimental.macros
import com.github.ivellien.pgquery.parser.nodes.{Node, ResTarget}

object PgQueryInterpolator {
  implicit class CompileTimeInterpolator(sc: StringContext) {

    def query(args: Any*): Node =
      macro MacrosImpl.parseQueryMacro

    def expr(args: Any*): ResTarget =
      macro MacrosImpl.parseExprMacro
  }
}
