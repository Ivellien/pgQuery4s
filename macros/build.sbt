organization := "com.github.ivellien"
version := "0.1"
scalaVersion := "2.13.5"
name := "macros"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

val root = (project in file(".."))

fork := true
javaOptions += s"-Djava.library.path=${baseDirectory.in(root).value / "parser" / "lib"}"
