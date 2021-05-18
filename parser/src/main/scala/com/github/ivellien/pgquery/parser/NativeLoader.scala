package com.github.ivellien.pgquery.parser

import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.io.IOUtils

import java.io._
import java.net.URL
import java.nio.file.{Files, Paths}
import scala.util.{Failure, Success, Try, Using}

object NativeLoader extends LazyLogging {
  def fromResources(libName: String): Unit = {
    extractLibrary(libName) match {
      case Success(libraryPath) => System.load(libraryPath.getAbsolutePath)
      case Failure(exception) =>
        throw new RuntimeException(
          s"Could not find library $libName in resources.",
          exception
        )
    }
  }

  private def extractLibrary(library: String): Try[File] = {
    val fullName = s"lib$library.$platformSuffix"
    val lib = this.getClass.getClassLoader.getResource("lib/" + fullName)

    if (lib.getProtocol == "file") {
      urlToFile(lib)
    } else {
      unpackFromJarToTemp(fullName, lib)
    }
  }

  private def urlToFile(lib: URL): Try[File] = {
    Try(Paths.get(lib.toURI).toFile)
  }

  private def unpackFromJarToTemp(fullName: String, lib: URL): Try[File] = {
    val dir = Files.createTempDirectory("pgquery4s")
    val dest = dir.resolve(fullName).toFile
    dest.deleteOnExit()

    Using.Manager { use =>
      val in = use(lib.openStream())
      val out = use(new FileOutputStream(dest))

      IOUtils.copy(in, out)
      dest
    }
  }

  private def platformSuffix: String =
    System.getProperty("os.name").toLowerCase match {
      case mac if mac.contains("mac")   => "dylib"
      case tux if tux.contains("linux") => "so"
      case other =>
        throw new RuntimeException(s"Unknown operating system $other")
    }

}
