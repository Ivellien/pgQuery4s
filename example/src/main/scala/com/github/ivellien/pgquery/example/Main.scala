package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.ImplicitConversions._
import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.{Node, ResTarget}
import com.github.ivellien.pgquery.parser.nodes.values.A_Const
import com.github.ivellien.pgquery.example.DatabaseConnection._
import com.github.ivellien.pgquery.example.Classroom.insertClassroom
import com.github.ivellien.pgquery.example.Student.insertStudent
import com.typesafe.scalalogging.LazyLogging
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import fs2.Stream

object Main extends LazyLogging {
  val JohnName = "John Smith"
  val JohnGrade = 5

  implicit class NodeToFragment(n: Node) {
    def fr: Fragment = Fragment(n.query, List.empty)
  }

  def updateDB(node: Node): Unit =
    node.fr.update.run.transact(ExampleTransactor).unsafeRunSync()

  def selectStudentsFromDB(node: Node): List[Student] = {
    node.fr
      .query[Student]
      .to[List]
      .transact(ExampleTransactor)
      .unsafeRunSync()
  }

  def main(args: Array[String]): Unit = {
    updateDB(Classroom.ClassroomTable)
    updateDB(Student.StudentTable)

    logger.info("Created tables.")

    // INSERT INTO queries - Queries are created via 'query' string interpolator - compile time checked
    val insertClassrooms: ConnectionIO[Int] = for {
      _ <- insertClassroom("1.A").fr.update.run
      _ <- insertClassroom("2.A").fr.update.run
      _ <- insertClassroom("3.B").fr.update.run
      classroomCountQuery <- query"SELECT count(*) FROM classrooms"
      classrooms <- classroomCountQuery.fr
        .query[Int]
        .unique
    } yield classrooms

    val insertStudents: ConnectionIO[Int] = for {
      _ <- insertStudent(JohnName, JohnGrade, 1).fr.update.run
      _ <- insertStudent("Petr", 4, 1).fr.update.run
      _ <- insertStudent("Honza", 3, 2).fr.update.run
      _ <- insertStudent("xxxx", 2, 3).fr.update.run
      _ <- insertStudent("4234", 2, 3).fr.update.run
      studentCountQuery <- query"SELECT count(*) FROM students"
      students <- studentCountQuery.fr
        .query[Int]
        .unique
    } yield students

    val populateTables = for {
      classroomCount <- insertClassrooms
      studentCount <- insertStudents
    } yield s"There are now $classroomCount classrooms and $studentCount students."

    logger.info(
      populateTables.transact(ExampleTransactor).unsafeRunSync()
    )

    // Selecting different columns with different expressions - all covered by single prepared query
    def selectStudentAST(
        columnName: ResTarget,
        expr: ResTarget
    ): Node =
      query"SELECT $columnName FROM students WHERE $expr"

    logger.info(
      selectStudentsFromDB(selectStudentAST("*", "age = 2")).toString
    )
    logger.info(
      selectStudentAST("name", "name LIKE 'John%'").fr
        .query[String]
        .to[List]
        .transact(ExampleTransactor)
        .unsafeRunSync()
        .toString
    )

    // Example of nesting expressions into queries.
    def nestedSelectAST(age: A_Const): Node =
      selectStudentAST("*", expr"age > $age")

    logger.info(
      selectStudentsFromDB(nestedSelectAST(2)).toString
    )
    logger.info(
      selectStudentsFromDB(nestedSelectAST(4)).toString
    )

    // Example of usage of interpolator for expressions
    val expression = expr"students.classroom_id = classrooms.classroom_id"

    logger.info(
      selectStudentsFromDB(
        query"SELECT * FROM students INNER JOIN classrooms ON ($expression)"
      ).toString
    )

    updateDB(query"DROP TABLE students")
    updateDB(query"DROP TABLE classrooms")
    logger.info("Dropped tables.")
  }
}
