package com.jhn.scala.files

abstract class DirEntry(val parentPath:String, val name: String) {
  def path:String = {
    val separatorIfNeccessary =
      if (parentPath.equals(Directory.ROOT_PATH)) ""
      else Directory.SEPARATOR
    s"$parentPath$separatorIfNeccessary$name"
  }

  def asDirectory: Directory

  def asFile: File

  def getType:String

  def isFile:Boolean

  def isDirectory:Boolean
}
