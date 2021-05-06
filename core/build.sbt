organization := "com.github.ivellien"
version := "0.1"
scalaVersion := "2.13.5"
name := "core"
scalacOptions += "-Ymacro-annotations"

val root = (project in file(".."))
