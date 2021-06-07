package com.github.ivellien.pgquery.example

import java.sql.{Connection, DriverManager, ResultSet}

object DatabaseConnection {
  private val driver = "org.postgresql.Driver"
  private val url = "jdbc:postgresql://localhost/pgquery_example"
  private val username = "postgres"
  private val password = "postgres"

  var connection: Connection = null

  try {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
  } catch {
    case e: Throwable =>
      e.printStackTrace()
  }

  def executeQuery(query: String): Unit = {
    println(s"Executing query - $query")
    try {
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery(query)
      printResultSet(resultSet)
    } catch {
      case e: Throwable =>
        println(e.toString)
    }
  }

  def updateQuery(query: String): Unit = {
    try {
      val statement = connection.createStatement()
      statement.executeUpdate(query)
    } catch {
      case e: Throwable =>
        println(e.toString)
    }
  }

  /*
    Source: https://coderwall.com/p/609ppa/printing-the-result-of-resultset
   */
  private def printResultSet(resultSet: ResultSet): Unit = {
    val rsmd = resultSet.getMetaData
    val columnsNumber = rsmd.getColumnCount
    while (resultSet.next) {
      for (i <- 1 to columnsNumber) {
        if (i > 1) print(", ")
        val columnValue = resultSet.getString(i)
        print(s"${rsmd.getColumnName(i)} $columnValue")
      }
      println("")
    }
  }
}
