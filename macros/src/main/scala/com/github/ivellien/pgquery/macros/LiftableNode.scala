package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.liftable.LiftableCaseClassImpls
import com.github.ivellien.pgquery.liftable.LiftableEnumerationImpls
import com.github.ivellien.pgquery.parser.nodes._

trait LiftableNode
    extends LiftableCaseClassImpls
    with LiftableEnumerationImpls {

  import c.universe._

  private def lift[T](t: T)(implicit l: Liftable[T]) = l(t)

  implicit val _liftableNode: Liftable[Node] = {
    case n: NodeString => lift[NodeString](n)
    case n: A_Const    => lift[A_Const](n)
    case n: A_Expr     => lift[A_Expr](n)
    // and many, many more ...
  }
}
