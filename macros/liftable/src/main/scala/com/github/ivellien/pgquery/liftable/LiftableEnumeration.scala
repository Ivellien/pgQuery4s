package com.github.ivellien.pgquery.liftable

// could be a blackbox, yet the case class one works only as a whitebox

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * Adapted LiftableCaseClass.
  */
class LiftableEnumeration(override val c: whitebox.Context)
    extends GenericLiftable {

  def impl[T <: Enumeration#Value: c.WeakTypeTag]: c.Tree = {
    import c.universe._

    val T = weakTypeOf[T]
    val symbolT = T.typeSymbol

    val E = T match { case TypeRef(outer, _, _) => outer }
    val symbolE = E.typeSymbol

    val arguments = q"List(Literal(Constant(value.id)))"

    val reflect = q"Apply(${objectName(symbolE)}, $arguments)"

    instance(typeClassName(symbolT), T, reflect)
  }
}

trait LiftableEnumerationImpls {

  val c: whitebox.Context
  import c.universe._

  implicit def _liftableEnumeration[T <: Enumeration#Value]: Liftable[T] =
    macro LiftableEnumeration.impl[T]

}
