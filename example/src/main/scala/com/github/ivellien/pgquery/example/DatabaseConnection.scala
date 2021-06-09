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
    "docker",
    "docker",
    Blocker.liftExecutionContext(
      ExecutionContexts.synchronous
    )
  )

  val y = transactor.yolo
  import y._

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
      student <- Fragment(query, List.empty).query[String].to[List]
    } yield student

  def selectAge(query: String): ConnectionIO[List[Int]] =
    for {
      student <- Fragment(query, List.empty).query[Int].to[List]
    } yield student

  def insertStudent(query: String): ConnectionIO[Student] =
    for {
      _ <- Fragment(query, List.empty).update.run
      id <- Fragment(query"select lastval()".query, List.empty)
        .query[Long]
        .unique
      p <- Fragment(
        query"select student_id, name, age, classroom_id from students where student_id = ${id.toInt: A_Const}".query,
        List.empty
      ).query[Student].unique
    } yield p

  def addStudent(name: String, age: Int, classroom_id: Int): Unit = {
    insertStudent(
      query"INSERT INTO students (name, age, classroom_id) VALUES ($name, $age, $classroom_id)".query
    ).quick.unsafeRunSync()
  }

  def insertClassroom(query: String): ConnectionIO[Classroom] =
    for {
      _ <- Fragment(query, List.empty).update.run
      id <- Fragment(query"select lastval()".query, List.empty)
        .query[Long]
        .unique
      p <- Fragment(
        query"select classroom_id, name from classrooms where classroom_id = ${id.toInt: A_Const}".query,
        List.empty
      ).query[Classroom].unique
    } yield p

  def addClassroom(name: String): Unit = {
    insertClassroom(
      query"INSERT INTO classrooms (name) VALUES ($name)".query
    ).quick.unsafeRunSync()
  }
}
