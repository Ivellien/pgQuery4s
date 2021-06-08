package com.github.ivellien.pgquery.example

import doobie._
import doobie.implicits._
import cats.effect._
import com.typesafe.scalalogging.LazyLogging

object DatabaseConnection extends LazyLogging {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  private val transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/pgquery_example",
    "postgres",
    "postgres",
    Blocker.liftExecutionContext(
      ExecutionContexts.synchronous
    )
  )

  def executeQuery[T](query: Query0[T]): Unit = {
    logger.info("Executing query - " + query.sql)
    val rows: List[T] = query
      .to[List]
      .transact(transactor)
      .unsafeRunSync()
      .take(5)
    for (row <- rows) logger.info(row.toString)
  }

  def updateQuery(query: String): Unit = {
    logger.info("Updating query - " + query)
    Fragment(query, List.empty).update.run
      .transact(transactor)
      .unsafeRunSync()
  }
}
