package com.github.ivellien.pgquery.liftable

// could be a blackbox, yet the case class one works only as a whitebox

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * Adapted LiftableCaseClass.
  */
object LiftableCaseObject extends GenericLiftable {

  def impl[T: c.WeakTypeTag](c: whitebox.Context): c.Tree = {
    import c.universe._

    // hack: adaptation of the original code - expects to be called from a macro which contains
    // a context `c` - which is kind of a convention at this point

    val T = weakTypeOf[T]
    val symbol = T.typeSymbol

    if (!(symbol.asClass.isModuleClass && symbol.asClass.isCaseClass))
      c.abort(c.enclosingPosition, s"$symbol is not a case object")
    if (!symbol.isStatic)
      c.abort(c.enclosingPosition, s"$symbol is not static")

    val reflect = q"reify(${c.mirror.staticModule(symbol.fullName)}).tree"

    getResultTree(c)(T, symbol, reflect)
  }
}

trait LiftableCaseObjectImpls {

  val c: whitebox.Context
  import c.universe._

  implicit def _liftableCaseObject[T]: Liftable[T] =
    macro LiftableCaseObject.impl[T]

}
