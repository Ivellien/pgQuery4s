package Enums

import io.circe.Decoder
import io.circe.DecodingFailure


object A_Expr_Kind extends Enumeration {
  val AEXPR_OP, /* normal operator */
  AEXPR_OP_ANY, /* scalar op ANY (array) */
  AEXPR_OP_ALL, /* scalar op ALL (array) */
  AEXPR_DISTINCT, /* IS DISTINCT FROM - name must be "=" */
  AEXPR_NOT_DISTINCT, /* IS NOT DISTINCT FROM - name must be "=" */
  AEXPR_NULLIF, /* NULLIF - name must be "=" */
  AEXPR_OF, /* IS [NOT] OF - name must be "=" or "<>" */
  AEXPR_IN, /* [NOT] IN - name must be "=" or "<>" */
  AEXPR_LIKE, /* [NOT] LIKE - name must be "~~" or "!~~" */
  AEXPR_ILIKE, /* [NOT] ILIKE - name must be "~~*" or "!~~*" */
  AEXPR_SIMILAR, /* [NOT] SIMILAR - name must be "~" or "!~" */
  AEXPR_BETWEEN, /* name must be "BETWEEN" */
  AEXPR_NOT_BETWEEN, /* name must be "NOT BETWEEN" */
  AEXPR_BETWEEN_SYM, /* name must be "BETWEEN SYMMETRIC" */
  AEXPR_NOT_BETWEEN_SYM, /* name must be "NOT BETWEEN SYMMETRIC" */
  AEXPR_PAREN /* nameless dummy node for parentheses */
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
