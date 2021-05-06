package com.github.ivellien.pgquery.parser

import com.github.ivellien.pgquery.parser.nodes.{
  EmptyNode,
  Node,
  RangeVar,
  RawStmt,
  SelectStmt
}

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "SELECT $1 WHERE $2"

    println(PgQueryParser.wrapper.pgQueryParse(input))
    println(PgQueryParser.json(input))
    println(PgQueryParser.parseTree(input))
    val x = PgQueryParser.parseTree(input)
    val asdas: Node = x match {
      case Right(x) =>
        x.headOption
          .getOrElse(EmptyNode)
          .traverseAndReplace(
            x.head,
            Array(
              RangeVar(None, None, None, false, None, None, None),
              RangeVar(None, None, None, false, None, None, None)
            )
          )
      case _ =>
        println("Failed.")
        EmptyNode
    }
    println(asdas)
    println(PgQueryParser.prettify(input))
  }
}
