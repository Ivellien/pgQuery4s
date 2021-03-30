import Nodes.Node
import io.circe._
import io.circe.parser._

import scala.collection.mutable.ArrayBuffer


class PgQueryWrapper {
  System.loadLibrary("PgQueryWrapper")

  @native def pgQueryParse(query: String): String
}

object PgQueryWrapper {
  def main(args: Array[String]): Unit = {
    val sample = new PgQueryWrapper
    val input: String = "select text, ez FROM newTable WHERE x = 5"
    val text: String = sample.pgQueryParse(input)
    val data: Json = parse(text).getOrElse(Json.Null)
    val x: Node = Node(data.hcursor.downArray)
    println(x.toQuery())
  }
}