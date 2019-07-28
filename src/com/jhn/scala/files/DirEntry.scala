package com.jhn.scala.files

abstract class DirEntry(val parentPath:String, val name: String) {
  def path:String = s"$parentPath${Directory.SEPARATOR}$name"

  def asDirectory: Directory
}
