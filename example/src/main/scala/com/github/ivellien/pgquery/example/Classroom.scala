package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.Node
import com.github.ivellien.pgquery.core.ImplicitConversions._

object Classroom {
  val classroomTable: Node =
    query"""
        CREATE TABLE classrooms (
          class_id serial PRIMARY KEY,
          name VARCHAR (255) NOT NULL
          )
      """

  def addClassroom(name: String): Node = {
    query"INSERT INTO classrooms (name) VALUES ($name)"
  }

}
