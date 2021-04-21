package com.github.ivellien.pgquery.macros

import com.github.ivellien.pgquery.liftable.LiftableCaseClassImpls
import com.github.ivellien.pgquery.parser.nodes.A_Const
import com.github.ivellien.pgquery.parser.nodes.Node
import com.github.ivellien.pgquery.parser.nodes.NodeString

trait LiftableNode extends LiftableCaseClassImpls {

  import c.universe._

  private def lift[T](t: T)(implicit l: Liftable[T]) = l(t)

  implicit val liftNode: Liftable[Node] = {
    case n @ NodeString(_) => lift[NodeString](n)
    case n @ A_Const(_, _) => lift[A_Const](n)
    // and many, many more ...
  }
}
