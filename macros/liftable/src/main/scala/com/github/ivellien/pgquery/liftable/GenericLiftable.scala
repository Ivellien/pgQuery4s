package com.github.ivellien.pgquery.liftable

import scala.reflect.macros.whitebox

trait GenericLiftable {
  val c: whitebox.Context
  import c.universe._

  protected def objectName(symbol: Symbol): Tree =
    q"reify(${c.mirror.staticModule(symbol.fullName)}).tree"

  protected def typeClassName(symbol: c.Symbol): c.TermName =
    TermName(s"${symbol.name.encodedName}Liftable")

  protected def instance(
      typeClassName: c.TermName,
      T: c.Type,
      reflect: c.Tree
  ): c.Tree = {
    q"""
       implicit object $typeClassName extends Liftable[$T] {
         def apply(value: $T): Tree = $reflect
       }
       $typeClassName
     """
  }
}
