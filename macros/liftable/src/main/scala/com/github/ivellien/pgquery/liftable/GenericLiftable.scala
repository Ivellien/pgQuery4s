package com.github.ivellien.pgquery.liftable

import scala.reflect.macros.whitebox

trait GenericLiftable {
  def getResultTree(
      c: whitebox.Context
  )(T: c.Type, symbol: c.Symbol, reflect: c.Tree): c.Tree = {
    import c.universe._

    val implicitName = TermName(symbol.name.encodedName.toString ++ "Liftable")
    q"""
           implicit object $implicitName extends Liftable[$T] {
                    def apply(value: $T): Tree = $reflect
           }
           $implicitName
     """
  }
}
