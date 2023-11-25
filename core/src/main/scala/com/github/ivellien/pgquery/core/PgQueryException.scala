package com.github.ivellien.pgquery.core

// Define PgQueryException to extend Exception
class PgQueryException(message: String, cause: Throwable = null) extends Exception(message, cause)
