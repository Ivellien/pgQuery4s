package com.github.ivellien.pgquery.core

import scala.language.experimental.macros
import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.nodes.{
  Node,
  RawStmt,
  ResTarget,
  SelectStmt
}

object PgQueryInterpolator {
  def mergeInterpolator(
      sc: StringContext,
      args: Any*
  ): java.lang.StringBuilder = {
    val stringContextIterator = sc.parts.iterator
    val argsIterator = args.iterator

    val sb = new java.lang.StringBuilder(stringContextIterator.next())

    while (argsIterator.hasNext) {
      sb.append(argsIterator.next().toString)
      sb.append(stringContextIterator.next())
    }
    sb
  }

  implicit class PgInterpolator(val sc: StringContext) extends AnyVal {
    def Expr(args: Any*): Option[ResTarget] = {
      val sb = mergeInterpolator(sc, args)
      val result: List[Node] =
        PgQueryParser.parseTree("SELECT " + sb.toString).getOrElse(List.empty)
      println(result)
      result.headOption match {
        case None => None
        case Some(
            RawStmt(
              None,
              None,
              Some(
                SelectStmt(
                  _,
                  _,
                  _,
                  _,
                  _,
                  _,
                  _,
                  _,
                  _,
                  _,
                  _,
                  _,
                  List(x),
                  _,
                  _,
                  _,
                  _
                )
              )
            )
            ) =>
          Some(x)
      }
    }

    def q(args: Any*): String = {
      val sb = mergeInterpolator(sc, args)

      PgQueryParser.parseTree(sb.toString).toString
    }

    def pr(args: Any*): String = {
      val sb = mergeInterpolator(sc, args)

      PgQueryParser.prettify(sb.toString)
    }
  }

}
