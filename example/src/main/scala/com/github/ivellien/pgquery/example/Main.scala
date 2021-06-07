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
    updateQuery(Classroom.classroomTable.query)
    updateQuery(Student.studentTable.query)

    /*
    INSERT INTO queries
      - Queries are created via 'query' string interpolator - compile time checked
     */
    val x = "John Smith"
    val grade = 5
    updateQuery(Student.addStudent(x, grade).query)
    updateQuery(Student.addStudent("Petr", 4).query)
    updateQuery(Student.addStudent("Honza", 3).query)
    updateQuery(Student.addStudent("xxxx", 2).query)
    updateQuery(Student.addStudent("4234", 2).query)

    updateQuery(Classroom.addClassroom("1.A").query)
    updateQuery(Classroom.addClassroom("2.A").query)
    updateQuery(Classroom.addClassroom("3.B").query)

    /*
    Prints all students and their names within 'students' table
     */
    var res = executeQuery("SELECT * FROM students")

    def selectStudentAST(
        expr: ResTarget,
        columnName: ResTarget
    ): Node =
      query"SELECT $columnName FROM students WHERE $expr"

    res = executeQuery(selectStudentAST("age = 2", "age").query)

    res = executeQuery(
      selectStudentAST("name LIKE 'John%'", "name").query
    )

    res = executeQuery(
      selectStudentAST("age > 3", "*").query
    )

    /*
    Example of nesting queries.
     */
    def nestedSelectAST(age: A_Const): Node = {
      selectStudentAST(expr"age > $age", "*")
    }

    res = executeQuery(nestedSelectAST(2).query)
    res = executeQuery(nestedSelectAST(4).query)

    updateQuery("DROP TABLE students")
    updateQuery("DROP TABLE classrooms")
  }
}
