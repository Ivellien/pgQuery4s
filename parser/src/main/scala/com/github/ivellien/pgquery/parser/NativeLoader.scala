package com.github.ivellien.pgquery.parser

import com.typesafe.scalalogging.LazyLogging

import java.io._

object NativeLoader extends LazyLogging {
  // Using approach described here
  // https://stackoverflow.com/questions/12036607/bundle-native-dependencies-in-runnable-jar-with-maven/12040310#12040310
  def loadLibrary(library: String): Unit = {
    saveLibrary(library) match {
      case Some(libraryPath) => System.load(libraryPath)
      case _ =>
        logger.info(
          "Could not find library " + library + " - trying lookup through System.loadLibrary()."
        )
        System.loadLibrary(library)
    }
  }

  def saveLibrary(library: String): Option[String] = {
    val libName: String = "lib" + library + ".so" // Linux-only
    val src: InputStream =
      this.getClass.getClassLoader.getResourceAsStream("lib/" + libName)
    val tmpDirName: String = System.getProperty("java.io.tmpdir")
    val tmpDir = new File(tmpDirName)
    tmpDir.mkdir()
    val dest: File = File.createTempFile(library + "-", ".tmp", tmpDir)
    dest.deleteOnExit()

    val out = new FileOutputStream(dest)
    writeToFile(new BufferedInputStream(src), new BufferedOutputStream(out))

    src.close()
    out.close()
    Some(dest.getAbsolutePath())
  }

  def writeToFile(
      inputStream: BufferedInputStream,
      outputStream: BufferedOutputStream
  ): Unit = {
    val buffer = new Array[Byte](32 * 1024)
    Iterator.continually(inputStream.read(buffer)).takeWhile(_ > 0).foreach {
      bytesRead =>
        outputStream.write(buffer, 0, bytesRead)
        outputStream.flush()
    }
  }
}
