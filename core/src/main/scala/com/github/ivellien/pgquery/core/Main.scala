package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.core.PgQueryInterpolator.PgInterpolator
import com.github.ivellien.pgquery.macros.Macros
import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.nodes.Node

object Main {
  def main(args: Array[String]): Unit = {
    val input: String =
      "SELECT $1 WHERE $3"

    println(query("$1", "name LIKE john"))
    println(compile_time_query("address", "name LIKE john"))
  }

  // This is only checked at runtime, when the function is called
  def query(select: String, where: String): String =
    pr"SELECT $select WHERE $where"

  // This is checked at compile time using macro
  def compile_time_query(select: String, where: String): Node =
    ctq"SELECT name, $select, email WHERE $where"
}
