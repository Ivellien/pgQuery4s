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


  // Using macro to allow fail at compilation, when expression can't be created from string.
  implicit def string2expression(x: String): ResTarget =
    macro MacrosConversion.string2ResTarget

  implicit def int2expression(x: Int): ResTarget =
    ResTarget(None, None, Some(A_Const(Some(NodeInteger(x)), None)), None)


  implicit def string2NodeString(x: String): A_Const =
    A_Const(Some(NodeString(x)), None)

  implicit def int2NodeInteger(x: Int): A_Const =
    A_Const(Some(NodeInteger(x)), None)
}
