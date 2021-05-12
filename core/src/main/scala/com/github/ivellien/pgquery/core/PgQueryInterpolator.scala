package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.macros.MacrosImpl

import scala.language.experimental.macros
import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.nodes.{EmptyNode, Node}
import com.typesafe.scalalogging.LazyLogging

object PgQueryInterpolator {
  implicit class PgInterpolator(val sc: StringContext) extends LazyLogging {

    def ctq(args: Any*): Node =
      macro MacrosImpl.parseQueryMacro

    def expr(args: Any*): Node =
      macro MacrosImpl.parseExprMacro

    def query(args: Any*): Node = {
      val stringContextIterator = sc.parts.iterator
      val argsIterator = args.iterator
      val sb = new java.lang.StringBuilder(stringContextIterator.next())
      while (argsIterator.hasNext) {
        sb.append(argsIterator.next().toString)
        sb.append(stringContextIterator.next())
      }

      PgQueryParser.parseTree(sb.toString) match {
        case Left(_) | Right(Nil) =>
          logger.error("Failed parsing of query.")
          EmptyNode
        case Right(nodeList: List[Node]) =>
          nodeList.head
      }
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
