import com.github.ivellien.pgquery.{PgQueryParser, PgQueryWrapper}
import com.github.ivellien.pgquery.nodes.Node
import io.circe.Json
import io.circe.parser.parse
import org.scalatest.FunSuite

class QueryTests extends FunSuite {
  test("parsing") {
    val input: String = "SELECT x FROM y WHERE x < 2 and x > 0 or x = 1"

    val parser = new PgQueryParser
    assert(parser.prettify(input) == input)
  }
}
