# pgQuery4s

Scala library accessing the *parse tree* of PostgreSQL queries. It is based on https://github.com/pganalyze/libpg_query, which uses the actual PostgreSQL server source to parse SQL queries and return the internal PostgreSQL parse tree.

Since this library is using native code the base C library has to be compiled for various OS - currently supporting MacOS and Linux. When building, the PostgresSQL server source, which is within [libpg_query](https://github.com/pganalyze/libpg_query) is built and linked using JNI.

## Installation
As mentioned before pgQuery4s uses libpg_query to get access to parse trees of SQL queries. Therefore, the native library has to be compiled on your system, running these commands from the root folder of the project:
```
git submodule init
git submodule update
cd parser/src/main/native/libpg_query
make
```
The pgQuery4s library itself can be then published locally.
```
sbt test publishLocal
```

And added to your sbt project
```
libraryDependencies ++= Seq(
  "com.github.ivellien" %% "pgquery4s-core" % pgquery4sVersion,
  "com.github.ivellien" %% "pgquery4s-parser" % pgquery4sVersion
)
```
## Contribution guidelines

We appreciate any type of contribution. If you find any bugs or have ideas for improvement, use GitHub issues or contact me via email.

Pull request guidelines
1. Provide description about your changes
2. Make sure that all existing tests are passing
3. If you are adding new feature, make sure it is properly tested

## Usage

### Taking advantage of compile time validation

To ease working and validating PostgreSQL queries, we provide custom string interpolators *query* and *expr*. These use macros to validate queries at compile time.

```scala
val expression = expr"x = 5" 
expression: ResTarget = ResTarget(None, None, Some(A_Expr(AExprOp, List(NodeString("=")), Some(ColumnRef(List(NodeString("x")), Some(7))), Some(A_Const(Some(NodeInteger(5)), Some(11))), Some(9))), Some(7))

query"SELECT * WHERE $expression" 
res0: RawStmt = RawStmt(
  None,
  None,
  Some(
    SelectStmt(
      None,
      Some(ResTarget(None, None, Some(A_Expr(AExprOp, List(NodeString("=")), Some(ColumnRef(List(NodeString("x")), Some(7))), Some(A_Const(Some(NodeInteger(5)), Some(11))), Some(9))), Some(7))),
      None,
      None,
      None,
      None,
      ,
      None,
      None,
      List(),
      List(),
      List(),
      List(ResTarget(None, None, Some(ColumnRef(List(A_Star), Some(7))), Some(7))),
      List(),
      List(),
      List(),
      List()
    )
  )
)
```

### Reparsing AST back to query

Each parse tree, represented as tree of *Nodes* can be turned back into valid PostgreSQL query. 

```scala
val parseTree: Node = query"SELECT *" 
parseTree: Node = RawStmt(
  None,
  None,
  Some(SelectStmt(None, None, None, None, None, None, , None, None, List(), List(), List(), List(ResTarget(None, None, Some(ColumnRef(List(A_Star), Some(7))), Some(7))), List(), List(), List(), List()))
)

parseTree.query
res0: String = "SELECT *"
```


### Runtime parsing query

In case you explicitly want query to be parsed at runtime, you can use `parse` method of PgQueryParser, which is internally used for compilation time validation as well.

```scala
PgQueryParser.parse("SELECT *")
res0: PgQueryParser.PgQueryResult[com.github.ivellien.pgquery.parser.nodes.Node] = Right(
  RawStmt(
    None,
    None,
    Some(
      SelectStmt(
        None,
        None,
        None,
        None,
        None,
        None,
        ,
        None,
        None,
        List(),
        List(),
        List(),
        List(
          ResTarget(
            None,
            None,
            Some(
              ColumnRef(
                List(A_Star),
                Some(7)
              )
            ),
            Some(7)
          )
        ),
        List(),
        List(),
        List(),
        List()
      )
    )
  )
)
```

## Example project

In example subproject, which is part of this project you can see integration with [doobie](https://tpolecat.github.io/doobie/). Queries are first created using pgQuery4s and then executed through connection created via doobie.

