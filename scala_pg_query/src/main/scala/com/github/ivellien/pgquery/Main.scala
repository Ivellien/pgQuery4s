package com.github.ivellien.pgquery

import com.github.ivellien.pgquery.nodes.Node
import io.circe.Json
import io.circe.parser.parse

object Main {
  def main(args: Array[String]): Unit = {
    val sample = new PgQueryWrapper
    val input: String = "UPDATE Customers SET ContactName = 'Alfred Schmidt', City= 'Frankfurt' WHERE CustomerID = 1"
    val text: String = sample.pgQueryParse(input)

    val data: Json = parse(text).getOrElse(Json.Null)
    val x = data.as[List[Node]]

    println(input)
    println(text)
    println(x)
    println(x.map(_.head.query))
  }
}
