package com.jhn.scala.files

import com.jhn.scala.filesystem.FileSystemExceptions

class File(override val parentPath: String, override val name: String, contents: String)
  extends DirEntry(parentPath, name) {
  def asDirectory: Directory = throw new FileSystemExceptions("File cannot be converted to directory")

  def getType: String = "File"

  def asFile: File = this

  def isFile:Boolean = true

  def isDirectory:Boolean = false

}


object File {
  def empty(parentPath: String, name:String): File = {
    new File(parentPath, name, "")
  }
}