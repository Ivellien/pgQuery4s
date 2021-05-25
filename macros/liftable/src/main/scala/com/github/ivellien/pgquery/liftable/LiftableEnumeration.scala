package com.github.ivellien.pgquery.liftable

// could be a blackbox, yet the case class one works only as a whitebox
import com.github.ivellien.pgquery.liftable.LiftableCaseObject.getResultTree

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * Adapted LiftableCaseClass.
  */
object LiftableEnumeration extends GenericLiftable {

  def impl[T <: Enumeration#Value: c.WeakTypeTag](
      c: whitebox.Context
  ): c.Tree = {
    import c.universe._
    import Flag._

    // hack: adaptation of the original code - expects to be called from a macro which contains
    // a context `c` - which is kind of a convention at this point

    val T = weakTypeOf[T]
    val symbolT = T.typeSymbol

    val E = T match { case TypeRef(outer, _, _) => outer }
    val symbolE = E.typeSymbol

    val objectName = q"reify(${c.mirror.staticModule(symbolE.fullName)}).tree"

    val arguments = q"List(Literal(Constant(value.id)))"

    val reflect = q"Apply($objectName, $arguments)"

    getResultTree(c)(T, symbolT, reflect)
  }
}

trait LiftableEnumerationImpls {

  val c: whitebox.Context
  import c.universe._

  implicit def _liftableEnumeration[T <: Enumeration#Value]: Liftable[T] =
    macro LiftableEnumeration.impl[T]

}
