package com.github.ivellien.pgquery.example

import doobie._
import doobie.implicits._
import cats.effect._
import com.typesafe.scalalogging.LazyLogging

object DatabaseConnection extends LazyLogging {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val ExampleTransactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/pgquery_example",
    "postgres",
    "",
    Blocker.liftExecutionContext(
      ExecutionContexts.synchronous
    )
  )
}
