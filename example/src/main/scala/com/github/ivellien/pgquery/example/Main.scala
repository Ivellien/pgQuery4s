package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes._
import com.github.ivellien.pgquery.core.ImplicitConversions._
import com.github.ivellien.pgquery.example.DatabaseConnection.{
  executeQuery,
  updateQuery
}
import com.github.ivellien.pgquery.parser.nodes.values.A_Const

import java.sql.ResultSet

object Main {

  def main(args: Array[String]): Unit = {
    /* CREATE TABLE queries */
    println(Student.studentTable)
    println(Student.studentTable.query)
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
    executeQuery("SELECT * FROM students")

    def selectStudentAST(
        expr: ResTarget,
        columnName: ResTarget
    ): Node =
      query"SELECT $columnName FROM students WHERE $expr"

    executeQuery(
      selectStudentAST("age = 2", "age").query
    )
    executeQuery(
      selectStudentAST("name LIKE 'John%'", "name").query
    )
    executeQuery(
      selectStudentAST("age > 3", "*").query
    )

    /*
    Example of nesting expressions into queries.
     */
    def nestedSelectAST(age: A_Const): Node = {
      selectStudentAST(expr"age > $age", "*")
    }

    executeQuery(nestedSelectAST(2).query)
    executeQuery(nestedSelectAST(4).query)

    val expression = expr"students.classroom_id = classrooms.classroom_id"
    executeQuery(
      query"SELECT * FROM students INNER JOIN classrooms ON ($expression)".query
    )

    updateQuery("DROP TABLE students")
    updateQuery("DROP TABLE classrooms")
  }
}
