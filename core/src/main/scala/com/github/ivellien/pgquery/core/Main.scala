package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.PgQueryInterpolator.PgInterpolator
import com.github.ivellien.pgquery.macros.Macros.parse_compile
import com.github.ivellien.pgquery.parser.nodes.{Node, ResTarget}

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "x = 5"

    val x: Option[ResTarget] = Expr"$input"
    x match {
      case Some(r: ResTarget) => println(compile_time_query(r))
      case _                  => println("Not ResTarget")
    }
    println(query("$1", "name LIKE john"))
//    println(compile_time_query("address", "name LIKE john"))
  }

  // This is only checked at runtime, when the function is called
  def query(select: String, where: String): String =
    pr"SELECT $select WHERE $where"

  // This is checked at compile time using macro
  def compile_time_query(where: ResTarget): Node = {
    val parseTree = parse_compile(pr"SELECT x WHERE $where")
    parseTree
  }

}
