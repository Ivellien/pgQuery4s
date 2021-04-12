package com.github.ivellien.pgquery

object Main {
  def main(args: Array[String]): Unit = {
//    val input: String =
//      "SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate FROM Orders INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID"
    val input: String =
      "SELECT customername, city, country FROM Customers ORDER BY (CASE WHEN city IS NULL THEN country ELSE city END)"

    val parser: PgQueryParser = new PgQueryParser

    println(parser.wrapper.pgQueryParse(input))
    println(parser.json(input))
    println(parser.parseTree(input))
    println(parser.prettify(input))

    // TODO CaseExpr / Union (rexpr, lexpr for SelectStmt)
  }
}
