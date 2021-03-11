class PgQueryWrapper {
  System.loadLibrary("PgQueryWrapper")

  val parse_tree: String = ""
  val stderr_buffer: String = ""
  @native def pgQueryParse(query: String): String
}

object PgQueryWrapper {
  def main(args: Array[String]): Unit = {
    val sample = new PgQueryWrapper
    val input = "SELECT 1"
    val text = sample.pgQueryParse(input)
    println(text)
  }
}
