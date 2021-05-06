package com.github.ivellien.pgquery.liftable

// could be a blackbox, yet the case class one works only as a whitebox
import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * Adapted LiftableCaseClass.
  */
object LiftableEnumeration {

  def impl[T <: Enumeration#Value: c.WeakTypeTag](
      c: whitebox.Context
  ): c.Tree = {
    import c.universe._
    import Flag._

    // hack: adaptation of the original code - expects to be called from a macro which contains
    // a context `c` - which is kind of a convention at this point

    val prefix = Select(
      Select(c.prefix.tree, TermName("c")),
      TermName("universe")
    )

    val T = weakTypeOf[T]
    val symbolT = T.typeSymbol

    val E = T match { case TypeRef(outer, _, _) => outer }
    val symbolE = E.typeSymbol

    /* Tree produced by the following quasiquote:
     * q"reify(${c.mirror.staticModule(symbolE.fullName)}).tree"
     */
    val objectName = Select(
      Apply(
        Select(prefix, TermName("reify")),
        List(Ident(c.mirror.staticModule(symbolE.fullName)))
      ),
      TermName("tree")
    )
    val value = "value"
    val arguments = List(
      Apply(
        Select(prefix, TermName("Literal")),
        List(
          Apply(
            Select(prefix, TermName("Constant")),
            List(Select(Ident(TermName("value")), TermName("id")))
          )
        )
      )
    )

    /* Tree produced by the following quasiquote:
     * q"Apply($objectName, List(..$arguments))"
     */
    val reflect = Apply(
      Select(prefix, TermName("Apply")),
      List(objectName, Apply(Ident(definitions.ListModule), arguments))
    )

    val implicitName = TermName(symbolT.name.encoded ++ "Liftable")
    /* Tree produced by the following quasiquote:
     * q"""
     *   implicit object $implicitName extends Liftable[$T] {
     *     def apply(value: $T): Tree = $reflect
     *   }
     *   $implicitName
     * """
     */

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

trait LiftableEnumerationImpls {

  val c: whitebox.Context
  import c.universe._

  implicit def _liftableEnumeration[T <: Enumeration#Value]: Liftable[T] =
    macro LiftableEnumeration.impl[T]

}
