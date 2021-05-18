package com.github.ivellien.pgquery.liftable

// could be a blackbox, yet the case class one works only as a whitebox
import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * Adapted LiftableCaseClass.
  */
object LiftableCaseObject {

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

//    val E = T match { case TypeRef(outer, _, _) => outer }
//    val symbolE = E.typeSymbol

    if (!symbol.asClass.isModuleClass)
      c.abort(c.enclosingPosition, s"$symbol is object")
    if (!symbol.asClass.isCaseClass)
      c.abort(c.enclosingPosition, s"$symbol is not a case class")
    if (!symbol.isStatic)
      c.abort(c.enclosingPosition, s"$symbol is not static")

    val value = "value"

    val reflect = Select(
      Apply(
        Select(prefix, TermName("reify")),
        List(Ident(c.mirror.staticModule(symbol.fullName)))
      ),
      TermName("tree")
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
