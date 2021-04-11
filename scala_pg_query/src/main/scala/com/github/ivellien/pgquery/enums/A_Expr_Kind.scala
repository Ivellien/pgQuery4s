package com.github.ivellien.pgquery.enums

import io.circe.{Decoder, DecodingFailure}

object A_Expr_Kind extends Enumeration with EnumerationDecoder {
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
}
