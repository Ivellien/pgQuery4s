package com.github.ivellien.pgquery.example

import doobie._
import doobie.implicits._
import cats.effect._
import com.github.ivellien.pgquery.core.ImplicitConversions._
import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.values.A_Const
import com.typesafe.scalalogging.LazyLogging

object DatabaseConnection extends LazyLogging {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  private val transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/pgquery_example",
    "postgres",
    "",
    Blocker.liftExecutionContext(
      ExecutionContexts.synchronous
    )
  )
  val y = transactor.yolo

  def updateQuery(query: String): Unit =
    Fragment(query, List.empty).update.run
      .transact(transactor)
      .unsafeRunSync()

  def selectStudent(query: String): ConnectionIO[List[Student]] =
    for {
      student <- Fragment(query, List.empty).query[Student].to[List]
    } yield student

  def selectName(query: String): ConnectionIO[List[String]] =
    for {
      name <- Fragment(query, List.empty).query[String].to[List]
    } yield name

  def selectAge(query: String): ConnectionIO[List[Int]] =
    for {
      age <- Fragment(query, List.empty).query[Int].to[List]
    } yield age

  def insertStudent(query: String): ConnectionIO[Student] =
    for {
      _ <- Fragment(query, List.empty).update.run
      id <- Fragment(query"select lastval()".query, List.empty)
        .query[Long]
        .unique
      student <- Fragment(
        query"select student_id, name, age, classroom_id from students where student_id = ${id.toInt: A_Const}".query,
        List.empty
      ).query[Student].unique
    } yield student

  def insertClassroom(query: String): ConnectionIO[Classroom] =
    for {
      _ <- Fragment(query, List.empty).update.run
      id <- Fragment(query"select lastval()".query, List.empty)
        .query[Long]
        .unique
      classroom <- Fragment(
        query"select classroom_id, name from classrooms where classroom_id = ${id.toInt: A_Const}".query,
        List.empty
      ).query[Classroom].unique
    } yield classroom
}
