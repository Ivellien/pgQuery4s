package com.github.ivellien.pgquery.liftable

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

object LiftableCaseClass {

  /* All the trees (except one that has a special comment) in this macro are hygienic
   * (and there is a test to check that). That is, all TermNames used
   * (specifically: Tree, Apply, Liftable, List, implicitly, reify) are fully qualified,
   * and will not bind to terms with same names, declared in the expansion context.
   * Each tree is commented with a simplified, unhygienic quasiquote, that explains
   * what that tree is supposed to do.
   *
   * Author: https://github.com/folone
   * Source: https://github.com/folone/scala/blob/d0b82f608ce6d6abfa991e6e6e0dfc92ce9d9d2f/src/reflect/scala/reflect/api/StandardLiftables.scala
   * License: Apache License, Version 2.0
   */
  def impl[T: c.WeakTypeTag](c: whitebox.Context): c.Tree = {
    import c.universe._

    // hack: adaptation of the original code - expects to be called from a macro which contains
    // a context `c` - which is kind of a convention at this point

    val T = weakTypeOf[T]
    val symbol = T.typeSymbol

    if (symbol.asClass.isModuleClass)
      c.abort(c.enclosingPosition, s"$symbol is an object")
    if (!symbol.asClass.isCaseClass)
      c.abort(c.enclosingPosition, s"$symbol is not a case class")
    if (!symbol.isStatic)
      c.abort(c.enclosingPosition, s"$symbol is not static")
    def fields(tpe: Type) =
      tpe.decls
        .collectFirst {
          case m: MethodSymbol if m.isPrimaryConstructor ⇒ m
        }
        .get
        .paramLists
        .head
        .map { field ⇒
          val name = field.name
          val typeSign = tpe.decl(name).typeSignature
          name -> typeSign
        }

    val constructor =
      q"reify(${c.mirror.staticModule(symbol.fullName)}).tree"

    val arguments = fields(T).map {
      case (name, typeSign) ⇒
        q"implicitly[Liftable[$typeSign]].apply(value.${name.toTermName})"
    }

    val reflect = q"Apply($constructor, $arguments)"

    val implicitName = TermName(symbol.name.encodedName.toString ++ "Liftable")

    q"""
       implicit object $implicitName extends Liftable[$T] {
         def apply(value: $T): Tree = $reflect
       }
       $implicitName
     """
  }
}

trait LiftableCaseClassImpls {

  val c: whitebox.Context
  import c.universe._

  implicit def _liftableCaseClass[T]: Liftable[T] =
    macro LiftableCaseClass.impl[T]

}
