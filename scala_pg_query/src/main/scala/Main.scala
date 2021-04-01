import io.circe.Json
import io.circe.parser.parse
import nodes.Node

object Main {
  def main(args: Array[String]): Unit = {
    val sample = new PgQueryWrapper
    val input: String = "select xoxo, momo FROM newTable WHERE x = 5"
    val text: String = sample.pgQueryParse(input)

    val data: Json = parse(text).getOrElse(Json.Null)
    val x = data.as[List[Node]]

    println(input)
    println(text)
    println(x)
    println(x.map(_.head.query))
  }
}
