package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.macros.MacrosImpl

import scala.language.experimental.macros
import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.PgQueryParser.PgQueryResult
import com.github.ivellien.pgquery.parser.nodes.{Node, ResTarget}
import com.typesafe.scalalogging.LazyLogging

object PgQueryInterpolator {
  implicit class CompileTimeInterpolator(sc: StringContext) {

    def ctq(args: Any*): Node =
      macro MacrosImpl.parseQueryMacro

    def cte(args: Any*): ResTarget =
      macro MacrosImpl.parseExprMacro
  }

  implicit class RuntimeInterpolator(sc: StringContext) extends LazyLogging {

    def rtq(args: Any*): PgQueryResult[Node] = {
      val stringContextIterator = sc.parts.iterator
      val argsIterator = args.iterator
      val sb = new java.lang.StringBuilder(stringContextIterator.next())
      while (argsIterator.hasNext) {
        sb.append(argsIterator.next().toString)
        sb.append(stringContextIterator.next())
      }

      PgQueryParser.parseTree(sb.toString)
    }

    def pr(args: Any*): String = {
      val stringContextIterator = sc.parts.iterator
      val argsIterator = args.iterator
      val sb = new java.lang.StringBuilder(stringContextIterator.next())
      while (argsIterator.hasNext) {
        sb.append(argsIterator.next().toString)
        sb.append(stringContextIterator.next())
      }

      PgQueryParser.prettify(sb.toString)
    }
  }
}
