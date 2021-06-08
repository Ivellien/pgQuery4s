import sbt.Keys.{fork, libraryDependencies}

val doobieVersion = "0.12.1"
val pgquery4sVersion = "0.1"
val logbackVersion = "1.2.3"
val scalaLoggingVersion = "3.9.3"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "com.github.ivellien" %% "pgquery4s-core" % pgquery4sVersion,
  "com.github.ivellien" %% "pgquery4s-parser" % pgquery4sVersion,
  "com.github.ivellien" %% "pgquery4s-macros" % pgquery4sVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
)

fork := true

lazy val example = project
  .settings(
    name := "example"
  )
