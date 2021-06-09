package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.example.DatabaseConnection._
import com.github.ivellien.pgquery.core.ImplicitConversions._
import com.github.ivellien.pgquery.parser.nodes.{ResTarget, Node}
import com.github.ivellien.pgquery.core.PgQueryInterpolator._
import com.github.ivellien.pgquery.parser.nodes.values.A_Const
import com.github.ivellien.pgquery.example.Student.{
  execSelectAge,
  execSelectName,
  execSelectStudent
}
import com.typesafe.scalalogging.LazyLogging

object Main extends LazyLogging {
  def main(args: Array[String]): Unit = {
    /* CREATE TABLE queries */
    updateQuery(Classroom.classroomTable.query)
    updateQuery(Student.studentTable.query)

    /*
    INSERT INTO queries
      - Queries are created via 'query' string interpolator - compile time checked
     */

    addClassroom("1.A")
    addClassroom("2.A")
    addClassroom("3.B")

    val x = "John Smith"
    val grade = 5
    addStudent(x, grade, 1)
    addStudent("Petr", 4, 1)
    addStudent("Honza", 3, 2)
    addStudent("xxxx", 2, 3)
    addStudent("4234", 2, 3)

    /*
    Prints all students and their names within 'students' table
     */
    execSelectStudent("SELECT * FROM students")

    def selectStudentAST(
        expr: ResTarget,
        columnName: ResTarget
    ): Node =
      query"SELECT $columnName FROM students WHERE $expr"

    execSelectAge(selectStudentAST("age = 2", "age").query)
    execSelectName(selectStudentAST("name LIKE 'John%'", "name").query)
    execSelectStudent(selectStudentAST("age > 3", "*").query)

    /*
    Example of nesting expressions into queries.
     */
    def nestedSelectAST(age: A_Const): Node = {
      selectStudentAST(expr"age > $age", "*")
    }

    execSelectStudent(nestedSelectAST(2).query)
    execSelectStudent(nestedSelectAST(4).query)

    val expression = expr"students.classroom_id = classrooms.classroom_id"
    execSelectStudent(
      query"SELECT * FROM students INNER JOIN classrooms ON ($expression)".query
    )

    updateQuery("DROP TABLE students")
    updateQuery("DROP TABLE classrooms")
  }
}
