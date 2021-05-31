package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.nodes.values.{
  A_Const,
  NodeInteger,
  NodeString
}

object ImplicitConversions {

  implicit def string2expression(x: String): ResTarget =
    PgQueryParser.parseExpression(x) match {
      case Right(resTarget) => resTarget
      case Left(error) =>
        ResTarget(None, None, Some(A_Const(Some(NodeString(x)), None)), None)
    }

  implicit def int2expression(x: Int): ResTarget =
    ResTarget(None, None, Some(A_Const(Some(NodeInteger(x)), None)), None)

  implicit def string2NodeString(x: String): A_Const =
    A_Const(Some(NodeString(x)), None)

  implicit def int2NodeInteger(x: Int): A_Const =
    A_Const(Some(NodeInteger(x)), None)
}
