import sbt._

object Dependencies {

  val scalaVersion = "2.13.5"

  private val circeVersion = "0.13.0"
  private val commonsIOVersion = "2.8.0"
  private val logbackVersion = "1.2.3"
  private val scalaLoggingVersion = "3.9.3"
  private val scalaTestVersion = "3.0.8"

  val circe: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras",
    "io.circe" %% "circe-parser",
  ).map(_ % circeVersion)

  val basic = Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scala-lang" % "scala-reflect" % scalaVersion
  )

  val commonsIO = "commons-io" % "commons-io" % commonsIOVersion

}
