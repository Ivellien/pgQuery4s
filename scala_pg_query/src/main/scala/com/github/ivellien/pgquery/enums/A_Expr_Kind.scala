package com.github.ivellien.pgquery.enums

import io.circe.{Decoder, DecodingFailure}

object A_Expr_Kind extends Enumeration {
  val AExprOp, /* normal operator */
  AexprOpAny, /* scalar op ANY (array) */
  AexprOpAll, /* scalar op ALL (array) */
  AexprDistinct, /* IS DISTINCT FROM - name must be "=" */
  AexprNotDistinct, /* IS NOT DISTINCT FROM - name must be "=" */
  AexprNullif, /* NULLIF - name must be "=" */
  AexprOf, /* IS [NOT] OF - name must be "=" or "<>" */
  AexprIn, /* [NOT] IN - name must be "=" or "<>" */
  AexprLike, /* [NOT] LIKE - name must be "~~" or "!~~" */
  AexprIlike, /* [NOT] ILIKE - name must be "~~*" or "!~~*" */
  AexprSimilar, /* [NOT] SIMILAR - name must be "~" or "!~" */
  AexprBetween, /* name must be "BETWEEN" */
  AexprNotBetween, /* name must be "NOT BETWEEN" */
  AexprBetweenSym, /* name must be "BETWEEN SYMMETRIC" */
  AexprNotBetweenSym, /* name must be "NOT BETWEEN SYMMETRIC" */
  AexprParen /* nameless dummy node for parentheses */
  = Value

  // scala 2.* Enums suck hard - scala 3 has improved a lot upon that
  // there are better 3th party implementations of enums, but for this purpose this should be enough
  private lazy val byId: Map[Int, A_Expr_Kind.Value] = values.map(v => v.id -> v).toMap

  /**
   * HERE: please study the following line
   * - byId.get(i) - returns Option[Value]
   * - toRight - turns Option into Either, more specifically its subtype Right
   * - DecodingFailure is used iff the Option was None as Left
   *
   * It is passed to flatMap. This is the monadic way of handling errors.
   * The computation is kept inside a Decoder.Result monad.
   * If it occurs, that we encounder some other error handling data structure, such as Option, we lift it to Result.
   *
   * Result[A] is defined as Either[DecodingFailure, A]
   */

  implicit val decoder: Decoder[A_Expr_Kind.Value] = c =>
    c.as[Int].flatMap(i => byId.get(i).toRight(DecodingFailure(s"No such ID: ${i}", c.history)))


}
