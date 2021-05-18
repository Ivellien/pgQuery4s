package com.github.ivellien.pgquery.parser

import io.circe.DecodingFailure

sealed trait PgQueryError

case object EmptyParsingResult extends PgQueryError

case class ParsingError(errorMessage: String = "") extends PgQueryError

case class FailureWhileParsing(decodingFailure: DecodingFailure)
    extends PgQueryError
