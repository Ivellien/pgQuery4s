package com.github.ivellien.pgquery.liftable

// could be a blackbox, yet the case class one works only as a whitebox

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * Adapted LiftableCaseClass.
  */
class LiftableCaseObject(override val c: whitebox.Context)
    extends GenericLiftable {

  def impl[T: c.WeakTypeTag]: c.Tree = {
    import c.universe._

    val T = weakTypeOf[T]
    val symbol = T.typeSymbol

    if (!(symbol.asClass.isModuleClass && symbol.asClass.isCaseClass))
      c.abort(c.enclosingPosition, s"$symbol is not a case object")
    if (!symbol.isStatic)
      c.abort(c.enclosingPosition, s"$symbol is not static")

    val reflect = objectName(symbol)

    instance(typeClassName(symbol), T, reflect)
  }
}

trait LiftableCaseObjectImpls {

  val c: whitebox.Context
  import c.universe._

  implicit def _liftableCaseObject[T]: Liftable[T] =
    macro LiftableCaseObject.impl[T]

}
