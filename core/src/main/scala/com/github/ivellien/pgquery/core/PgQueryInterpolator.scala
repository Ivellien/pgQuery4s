package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.macros.Macros
import scala.language.experimental.macros
import com.github.ivellien.pgquery.parser.PgQueryParser

object PgQueryInterpolator {

  implicit class PgInterpolator(val sc: StringContext) extends AnyVal {

    def ctq(args: Any*): String =
      compileTimeMacro(sc, args)

    def compileTimeMacro(sc: StringContext, args: Any*): String =
      macro Macros.parse_impl

    def q(args: Any*): String = {
      val stringContextIterator = sc.parts.iterator
      val argsIterator = args.iterator

      val sb = new java.lang.StringBuilder(stringContextIterator.next())

      while (argsIterator.hasNext) {
        sb.append(argsIterator.next().toString)
        sb.append(stringContextIterator.next())
      }
      PgQueryParser.parseTree(sb.toString).toString
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
