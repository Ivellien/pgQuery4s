import java.nio.file._
import scala.sys.process._

name := "Bachelor-project"
version := "0.1"
scalaVersion := "2.13.5"

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

val compileWrapper = taskKey[Seq[Path]]("compile wrapper code using gcc")
compileWrapper / sourceDirectory := sourceDirectory.value / "main" / "native"
compileWrapper / target := baseDirectory.value / "lib"

// can be specified using DYLD_LIBRARY_PATH (linux) and LD_LIBRARY_PATH (unix)
// as shown here https://stackoverflow.com/a/43122432
javaOptions in run += s"-Djava.library.path=${(compileWrapper / target).value}"
fork in run := true

compileWrapper := {

  // inspired by https://www.scala-sbt.org/1.x/docs/Combined+Pages.html#Custom+incremental+tasks
  // the task can be done incremental (recompiling only on source change ^^)
  // then compile can be made dependent on compileWrapper

  val (platform, suffix) = System.getProperty("os.name").toLowerCase match {
    case mac if mac.contains("mac") => ("darwin", "dylib")
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
