package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.parser.PgQueryParser
import com.github.ivellien.pgquery.parser.nodes.ResTarget
import com.github.ivellien.pgquery.parser.nodes.values.{A_Const, NodeInteger}
import com.typesafe.scalalogging.LazyLogging

import scala.reflect.macros.whitebox

class MacrosConversion(val c: whitebox.Context)
    extends LiftableNode
    with LazyLogging {

  import c.universe._

  val lift = implicitly[Liftable[ResTarget]]

  def string2ResTarget(x: c.Expr[String]): c.Expr[ResTarget] = {
    x match {
      case Expr(Literal(Constant(expr: String))) =>
        PgQueryParser.parseExpression(expr) match {
          case Right(value) => c.Expr(lift { value })
          case Left(error) =>
            c.abort(
              c.enclosingPosition,
              s"Error trying to implicitly convert String to ResTarget. \n" +
                s"Encountered error while parsing - $error"
            )
        }
      case _ =>
        c.abort(
          c.enclosingPosition,
          s"Error trying to implicitly convert String to ResTarget."
        )
    }
  }
}
