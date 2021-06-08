package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes._
import com.github.ivellien.pgquery.core.ImplicitConversions._
import com.github.ivellien.pgquery.example.DatabaseConnection.{
  executeQuery,
  updateQuery
}
import com.github.ivellien.pgquery.parser.nodes.values.A_Const

import doobie._

object Main {
  def createFragment(str: String): Fragment =
    Fragment(str, List.empty)

  def main(args: Array[String]): Unit = {
    /* CREATE TABLE queries */
    updateQuery(Classroom.classroomTable.query)
    updateQuery(Student.studentTable.query)

    /*
    INSERT INTO queries
      - Queries are created via 'query' string interpolator - compile time checked
     */

    updateQuery(Classroom.addClassroom("1.A").query)
    updateQuery(Classroom.addClassroom("2.A").query)
    updateQuery(Classroom.addClassroom("3.B").query)

    val x = "John Smith"
    val grade = 5
    updateQuery(Student.addStudent(x, grade, 1).query)
    updateQuery(Student.addStudent("Petr", 4, 1).query)
    updateQuery(Student.addStudent("Honza", 3, 2).query)
    updateQuery(Student.addStudent("xxxx", 2, 3).query)
    updateQuery(Student.addStudent("4234", 2, 3).query)

    /*
    Prints all students and their names within 'students' table
     */
    executeQuery(
      createFragment(
        "SELECT * FROM students"
      ).query[Student]
    )

    def selectStudentAST(
        expr: ResTarget,
        columnName: ResTarget
    ): Node =
      query"SELECT $columnName FROM students WHERE $expr"

    executeQuery(
      createFragment(
        selectStudentAST("age = 2", "age").query
      ).query[Int]
    )
    executeQuery(
      createFragment(
        selectStudentAST("name LIKE 'John%'", "name").query
      ).query[String]
    )
    executeQuery(
      createFragment(
        selectStudentAST("age > 3", "*").query
      ).query[Student]
    )

    /*
    Example of nesting expressions into queries.
     */
    def nestedSelectAST(age: A_Const): Node = {
      selectStudentAST(expr"age > $age", "*")
    }

    executeQuery(
      createFragment(
        nestedSelectAST(2).query
      ).query[Student]
    )
    executeQuery(
      createFragment(
        nestedSelectAST(4).query
      ).query[Student]
    )

    val expression = expr"students.classroom_id = classrooms.classroom_id"
    executeQuery(
      createFragment(
        query"SELECT * FROM students INNER JOIN classrooms ON ($expression)".query
      ).query[Student]
    )

    updateQuery("DROP TABLE students")
    updateQuery("DROP TABLE classrooms")
  }
}
