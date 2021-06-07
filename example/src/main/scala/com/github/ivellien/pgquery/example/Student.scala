package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.Node
import com.github.ivellien.pgquery.core.ImplicitConversions._

object Student {
  val studentTable: Node =
    query"""
         CREATE TABLE students (
            student_id serial PRIMARY KEY,
            name VARCHAR (255) NOT NULL UNIQUE,
            age INTEGER
            )
       """

  def addStudent(name: String, age: Int): Node = {
    query"INSERT INTO students (name, age) VALUES ($name, $age)"
  }

}
