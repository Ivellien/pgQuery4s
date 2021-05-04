organization := "com.github.ivellien"
version := "0.1"
scalaVersion := "2.13.5"
name := "liftable"
scalacOptions += "-Ymacro-annotations"

val root = (project in file(".."))

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

fork := true
javaOptions += s"-Djava.library.path=${baseDirectory.in(root).value / "parser" / "lib"}"
