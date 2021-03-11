# Bachelor-project

## Current status

Currently I am using JNI to access libpg_query library, being able to receive parse tree when given correct input.

To use what I have - set JAVA_HOME variable, so the cpp code can get jni.h library.
```
cd scala_pg_query/src/main/scripts
./run.sh
```

This script should return parse tree of query "SELECT 1".

`[{"RawStmt": {"stmt": {"SelectStmt": {"targetList": [{"ResTarget": {"val": {"A_Const": {"val": {"Integer": {"ival": 1}}, "location": 7}}, "location": 7}}], "op": 0}}}}]`

## Working on now

Trying out [SNA library](https://code.google.com/archive/p/scala-native-access/). That should help me get structs from C library to work with them directly in scala.

## Instructions

- Get familiar with the implementation of query parser in PostgreSQL

- Do a research of the existing libraries used to construct SQL queries in Scala programming language. Examine their pros and cons and see how your solution could fit into the existing library ecosystem.

- Design and implement a library which would support construction and composition of statically typed PostgreSQL queries in Scala programming language.

- To test the library create an extensive unit test suite, as well as implement an example application using the library.

- Make sure that your library is available in a form of a public repository with a working CI pipeline, contribution guidelines, etc.

- Discuss your results.
