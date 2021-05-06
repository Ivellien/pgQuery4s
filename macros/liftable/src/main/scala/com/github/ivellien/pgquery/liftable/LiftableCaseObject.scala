package com.github.ivellien.pgquery.liftable

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * Adapted LiftableCaseClass.
  */
object LiftableCaseObject {

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

    val prefix = Select(
      Select(c.prefix.tree, TermName("c")),
      TermName("universe")
    )

    val T = weakTypeOf[T]
    val symbol = T.typeSymbol

    if (!symbol.asClass.isModule)
      c.abort(c.enclosingPosition, s"$symbol is not a object")
    if (!symbol.isStatic)
      c.abort(c.enclosingPosition, s"$symbol is not static")
    def fields(tpe: Type) =
      tpe.declarations
        .collectFirst {
          case m: MethodSymbol if m.isPrimaryConstructor ⇒ m
        }
        .get
        .paramss
        .head
        .map { field ⇒
          val name = field.name
          val typeSign = tpe.declaration(name).typeSignature
          name → typeSign
        }
    /* Tree produced by the following quasiquote:
     * q"reify(${c.mirror.staticModule(symbol.fullName)}).tree"
     */
    val constructor = Select(
      Apply(
        Select(prefix, TermName("reify")),
        List(Ident(c.mirror.staticModule(symbol.fullName)))
      ),
      TermName("tree")
    )
    val value = "value"
    val arguments = fields(T).map {
      case (name, typeSign) ⇒
        /* Tree produced by the following quasiquote:
         * q"implicitly[Liftable[$typeSign]].apply(value.$name)"
         * Another way to do this would be:
         * q"""
         *   val v : $typeSign = value.$name
         *   q"$$v"
         * """
         *
         * This tree is not hygienic: TermName(value) will bind to value from
         * enclosing context, which is the tree being returned from the macro.
         * To ensure that its name does not change in one place, but stays
         * unchanged in another, it is placed in a dedicated val.
         */
        Apply(
          Select(
            TypeApply(
              Select(Ident(definitions.PredefModule), TermName("implicitly")),
              List(
                AppliedTypeTree(
                  Select(prefix, TypeName("Liftable")),
                  List(TypeTree(typeSign))
                )
              )
            ),
            TermName("apply")
          ),
          List(Select(Ident(TermName(value)), name))
        )
    }
    /* Tree produced by the following quasiquote:
     * q"Apply($constructor, List(..$arguments))"
     */
    val reflect = Apply(
      Select(prefix, TermName("Apply")),
      List(constructor, Apply(Ident(definitions.ListModule), arguments))
    )
    val implicitName = TermName(symbol.name.encoded ++ "Liftable")
    /* Tree produced by the following quasiquote:
     * q"""
     *   implicit object $implicitName extends Liftable[$T] {
     *     def apply(value: $T): Tree = $reflect
     *   }
     *   $implicitName
     * """
     */
    import Flag._

    Block(
      List(
        ModuleDef(
          Modifiers(IMPLICIT),
          implicitName,
          Template(
            List(
              AppliedTypeTree(
                Select(prefix, TypeName("Liftable")),
                List(TypeTree(T))
              )
            ),
            noSelfType,
            List(
              DefDef(
                Modifiers(),
                nme.CONSTRUCTOR,
                List(),
                List(List()),
                TypeTree(),
                Block(List(pendingSuperCall), Literal(Constant(())))
              ),
              DefDef(
                Modifiers(),
                TermName("apply"),
                List(),
                List(
                  List(
                    ValDef(
                      Modifiers(PARAM),
                      TermName(value),
                      TypeTree(T),
                      EmptyTree
                    )
                  )
                ),
                Select(prefix, TypeName("Tree")),
                reflect
              )
            )
          )
        )
      ),
      Ident(implicitName)
    )
  }
}

trait LiftableCaseObjectImpls {

  val c: whitebox.Context
  import c.universe._

  implicit def _liftableCaseObject[T]: Liftable[T] =
    macro LiftableCaseObject.impl[T]

}
