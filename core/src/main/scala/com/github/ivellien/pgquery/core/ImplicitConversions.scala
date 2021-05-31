package com.github.ivellien.pgquery.core

import com.github.ivellien.pgquery.macros.MacrosConversion
import scala.language.experimental.macros
import com.github.ivellien.pgquery.parser.nodes.ResTarget
import com.github.ivellien.pgquery.parser.nodes.values.{
  A_Const,
  NodeInteger,
  NodeString
}

object ImplicitConversions {

  implicit def string2expression(x: String): ResTarget =
    macro MacrosConversion.string2ResTarget

  implicit def int2expression(x: Int): ResTarget =
    macro MacrosConversion.int2ResTarget

  implicit def string2NodeString(x: String): A_Const =
    A_Const(Some(NodeString(x)), None)

  implicit def int2NodeInteger(x: Int): A_Const =
    A_Const(Some(NodeInteger(x)), None)
}
