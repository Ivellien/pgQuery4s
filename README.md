# pgQuery4s

Scala library accessing the *parse tree* of PostgreSQL queries. It is based on https://github.com/pganalyze/libpg_query, which uses the actual PostgreSQL server source to parse SQL queries and return the internal PostgreSQL parse tree.

Since this library is using native code the base C library has to be compiled for various OS - currently supporting MacOS and Linux. When building, the PostgresSQL server source, which is within [libpg_query](https://github.com/pganalyze/libpg_query) is built and linked using JNI.

## Installation

```scala
sbt compileWrapper
```

## Usage

### Runtime parsing query

```scala
PgQueryParser.parse("SELECT *")
```

### Taking advantage of compile time validation

To ease working and validating PostgreSQL queries, I have implemented custom string interpolator *query* and *expr*. These use macros to validate queries at compile time.

```scala
val expression = expr"x = 5"
query"SELECT * WHERE $expression"
```

### Reparsing AST back to query

Each parse tree, represented as tree of *Nodes* can be turned back into valid PostgreSQL query.

```scala
val parseTree: Node = query"SELECT *"
parseTree.query
=> "SELECT *"
```

## Instructions

- Get familiar with the implementation of query parser in PostgreSQL

- Do a research of the existing libraries used to construct SQL queries in Scala programming language. Examine their pros and cons and see how your solution could fit into the existing library ecosystem.

- Design and implement a library which would support construction and composition of statically typed PostgreSQL queries in Scala programming language.

- To test the library create an extensive unit test suite, as well as implement an example application using the library.

- Make sure that your library is available in a form of a public repository with a working CI pipeline, contribution guidelines, etc.

- Discuss your results.
