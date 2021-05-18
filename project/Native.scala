import sbt._

import java.nio.file._
import scala.sys.process._

object Native {

  def compileWrapper(src: File, out: File): Seq[Path] = {
    val (platform, suffix) = System.getProperty("os.name").toLowerCase match {
      case mac if mac.contains("mac") => ("darwin", "dylib")
      case tux if tux.contains("linux") => ("linux", "so")
      case other =>
        throw new RuntimeException(s"Unknown operating system $other")
    }

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

    assert(gccCmd.! == 0, "GCC exited with a non-zero status code")

    Seq[Path]()
  }
}
