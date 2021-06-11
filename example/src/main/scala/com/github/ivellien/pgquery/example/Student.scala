package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.Node
import com.github.ivellien.pgquery.core.ImplicitConversions._

case class Student(
    student_id: Long,
    name: String,
    age: Int,
    classroom_id: Long
)

object Student {
  val StudentTable: Node =
    query"""
         CREATE TABLE students (
            student_id serial PRIMARY KEY,
            name VARCHAR (255) NOT NULL UNIQUE,
            age INTEGER,
            classroom_id serial REFERENCES classrooms(classroom_id)
            )
       """

  def insertStudent(name: String, age: Int, classroom_id: Int): Node =
    query"INSERT INTO students (name, age, classroom_id) VALUES ($name, $age, $classroom_id)"
}
