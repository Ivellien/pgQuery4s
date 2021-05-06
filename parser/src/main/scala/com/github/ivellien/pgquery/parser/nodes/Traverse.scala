package com.github.ivellien.pgquery.parser.nodes

trait Traverse {
  def traverseAndReplace[T <: Node](
      node: T,
      replacements: Array[RangeVar],
      test: String = ""
  ): T = {
    for (i <- 1 to node.productIterator.toList.size)
      node.productElement(i - 1) match {
        case Some(n: ParamRef) =>
          println(test + "This should be replaced. With ID = " + n.number)
          val idx: Int = n.number - 1
          replacements(idx)
        case None          => println(test + "None node.")
        case Nil           => println(test + "Empty list.")
        case Some(n: Node) => traverseAndReplace(n, replacements, test + "\t")
        case n: List[Node] =>
          n.head.traverseAndReplace(n.head, replacements, test + "\t")
        case Some(n) => println(test + n)
        case _       => "Not something."
      }
    new ParamRef(1, Some(1)).asInstanceOf[T]
  }
}
