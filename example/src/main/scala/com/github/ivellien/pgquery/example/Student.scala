package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.Node
import com.github.ivellien.pgquery.example.DatabaseConnection._
import com.github.ivellien.pgquery.core.ImplicitConversions._
import com.github.ivellien.pgquery.example.DatabaseConnection.y._

case class Student(
    student_id: Long,
    name: String,
    age: Int,
    classroom_id: Long
)

object Student {
  val studentTable: Node =
    query"""
         CREATE TABLE students (
            student_id serial PRIMARY KEY,
            name VARCHAR (255) NOT NULL UNIQUE,
            age INTEGER,
            classroom_id serial REFERENCES classrooms(classroom_id)
            )
       """

  def addStudent(name: String, age: Int, classroom_id: Int): Unit = {
    insertStudent(
      query"INSERT INTO students (name, age, classroom_id) VALUES ($name, $age, $classroom_id)".query
    ).quick.unsafeRunSync()
  }

  def execSelectStudent(query: String): Unit =
    selectStudent(query).quick.unsafeRunSync()

  def execSelectName(query: String): Unit =
    selectName(query).quick.unsafeRunSync()

  def execSelectAge(query: String): Unit =
    selectAge(query).quick.unsafeRunSync()
}
