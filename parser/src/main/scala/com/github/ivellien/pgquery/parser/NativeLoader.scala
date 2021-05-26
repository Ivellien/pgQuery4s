package com.github.ivellien.pgquery.parser

import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.io.IOUtils

import java.net.URL
import java.nio.file.{Files, Path, Paths}
import scala.util.{Success, Try, Using}

object NativeLoader extends LazyLogging {
  def fromResources(libName: String): Unit = {
    extractLibrary(libName).foreach { libraryPath =>
      try {
        System.load(libraryPath.toAbsolutePath.toString)
      } catch {
        case e: UnsatisfiedLinkError if e.toString.contains("already loaded") =>
          throw new RuntimeException(
            "Could not load the native library. " +
              "Use `fork := true` in your sbt settings to avoid this issue.", e)
      }
    }
  }

  private def extractLibrary(library: String): Try[Path] = {
    val fullName = s"lib$library.$platformSuffix"
    val lib = this.getClass.getClassLoader.getResource("lib/" + fullName)

    if (lib.getProtocol == "file") {
      urlToFile(lib)
    } else {
      unpackFromJarToTemp(fullName, lib)
    }
  }

  private def urlToFile(lib: URL): Try[Path] = {
    Try(Paths.get(lib.toURI))
  }

  private def unpackFromJarToTemp(fullName: String, lib: URL): Try[Path] = {
    val dir = Paths.get(System.getProperty("java.io.tmpdir")).resolve("pgquery4s-lib")
    Files.createDirectories(dir)

    val dest = dir.resolve(fullName)
    dest.toFile.deleteOnExit()

    if (Files.exists(dest)) {
      Success(dest)
    } else {
      Using.Manager { use =>
        val in = use(lib.openStream())
        val out = use(Files.newOutputStream(dest))

        IOUtils.copy(in, out)
      }.map(_ => dest)
    }
  }

  private def platformSuffix: String =
    System.getProperty("os.name").toLowerCase match {
      case mac if mac.contains("mac") => "dylib"
      case tux if tux.contains("linux") => "so"
      case other =>
        throw new RuntimeException(s"Unknown operating system $other")
    }

}
