import sbt.Keys.version

import java.nio.file._

lazy val Version = "0.1"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.5",
  organization := "com.github.ivellien",
  version := Version,
  scalaVersion := Dependencies.scalaVersion,
  libraryDependencies ++= Dependencies.basic,
  fork := true
)

lazy val root: Project =
  project
    .settings(
      commonSettings,
      name := "pgquery4s"
    )
    .in(file("."))
    .aggregate(parser, macros, core, liftable)

lazy val parser: Project = project.settings(
  commonSettings,
  scalacOptions += "-Ymacro-annotations",
  libraryDependencies ++= Dependencies.circe,
  libraryDependencies += Dependencies.commonsIO,
  name := "pgquery4s-parser"
)

val compileWrapper: TaskKey[Seq[Path]] =
  taskKey[Seq[Path]]("compile wrapper code using gcc")

parser / compileWrapper := Native.compileWrapper(
  sourceDirectory.in(parser).value / "main" / "native",
  sourceDirectory.in(parser).value / "main" / "resources" / "lib"
)

lazy val macros: Project = project
  .dependsOn(parser, liftable)
  .settings(
    commonSettings,
    name := "pgquery4s-macros"
  )

lazy val liftable = project
  .in(file("macros/liftable"))
  .settings(
    commonSettings,
    name := "pgquery4s-liftable"
  )

lazy val core =
  project
    .dependsOn(macros, parser)
    .settings(
      commonSettings,
      name := "pgquery4s-core"
    )
