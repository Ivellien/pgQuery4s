package com.github.ivellien.pgquery.example

import com.github.ivellien.pgquery.core.PgQueryInterpolator.CompileTimeInterpolator
import com.github.ivellien.pgquery.parser.nodes.Node
import com.github.ivellien.pgquery.core.ImplicitConversions._
import com.github.ivellien.pgquery.example.DatabaseConnection.insertClassroom
import com.github.ivellien.pgquery.example.DatabaseConnection.y._

case class Classroom(
    classroom_id: Int,
    name: String
)

object Classroom {
  val classroomTable: Node =
    query"""
        CREATE TABLE classrooms (
          classroom_id serial PRIMARY KEY,
          name VARCHAR (255) NOT NULL
          )
      """

  def addClassroom(name: String): Unit = {
    insertClassroom(
      query"INSERT INTO classrooms (name) VALUES ($name)".query
    ).quick.unsafeRunSync()
  }
}
