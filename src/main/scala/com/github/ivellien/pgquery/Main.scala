package com.github.ivellien.pgquery

object Main {
  def main(args: Array[String]): Unit = {
//    val input: String =
//      "SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate FROM Orders INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID"
    val input: String =
      "DROP DATABASE databasename"

    val parser: PgQueryParser = new PgQueryParser

    println(parser.json(input))
    println(parser.parseTree(input))
    println(parser.prettify(input))

    // TODO IntoClause / CaseExpr / Union (rexpr, lexpr for SelectStmt)
  }
}
