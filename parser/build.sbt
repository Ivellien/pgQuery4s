import java.nio.file._
import scala.sys.process._

organization := "com.github.ivellien"
version := "0.1"
scalaVersion := "2.13.5"
name := "parser"
scalacOptions += "-Ymacro-annotations"

val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-generic-extras",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

val compileWrapper = taskKey[Seq[Path]]("compile wrapper code using gcc")
compileWrapper / sourceDirectory := sourceDirectory.value / "main" / "native"
compileWrapper / target := sourceDirectory.value / "main" / "resources" / "lib"

compileWrapper := {

  // inspired by https://www.scala-sbt.org/1.x/docs/Combined+Pages.html#Custom+incremental+tasks
  // the task can be done incremental (recompiling only on source change ^^)
  // then compile can be made dependent on compileWrapper

  val (platform, suffix) = System.getProperty("os.name").toLowerCase match {
    case mac if mac.contains("mac")   => ("darwin", "dylib")
    case tux if tux.contains("linux") => ("linux", "so")
    case other =>
      throw new RuntimeException(s"Unknown operating system $other")
  }

  val src = (compileWrapper / sourceDirectory).value
  val out = (compileWrapper / target).value

  s"mkdir -p ${out}".!!

  val gccCmd = (s"gcc " +
    s"-shared " +
    s"-O3 " +
    s"-I/usr/include " +
    s"-I${sys.env("JAVA_HOME")}/include " +
    s"-I${sys.env("JAVA_HOME")}/include/$platform " +
    s"-I${src}/libpg_query " +
    s"-L${src}/libpg_query " +
    s"${src}/PgQueryWrapper.cpp " +
    s"-lpg_query " +
    s"-o ${out}/libPgQueryWrapper.$suffix")

  gccCmd.!!

  Seq()
}
