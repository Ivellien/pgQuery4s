package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.Node
import com.github.ivellien.pgquery.core.ImplicitConversions._

case class Classroom(
    classroom_id: Int,
    name: String
)

object Classroom {
  val ClassroomTable: Node =
    query"""
        CREATE TABLE classrooms (
          classroom_id serial PRIMARY KEY,
          name VARCHAR (255) NOT NULL
          )
      """

  val ClassroomCountQuery: Node =
    query"SELECT count(*) FROM classrooms"

  def insertClassroom(name: String): Node =
    query"INSERT INTO classrooms (name) VALUES ($name)"
}
