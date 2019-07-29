package com.jhn.scala.files

import com.jhn.scala.filesystem.FileSystemExceptions

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

  def getType: String = "Directory"
  def replaceEntry(entryName: String, newEntry: Directory):Directory = Directory(parentPath, name,
    contents.filter(entry => !entry.name.equals(entryName)) :+ newEntry)

  def findEntry(entryName: String):DirEntry = {
    def findEntryHelper(name: String, contentList: List[DirEntry]) : DirEntry = {
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)
    }
    findEntryHelper(entryName, contents)
  }

  def addEntry(newEntry: DirEntry):Directory= Directory(parentPath, name, contents :+ newEntry)

  def getAllFoldersInPath():List[String]= {
      path.substring(1).split(Directory.SEPARATOR).toList.filter(x=> !x.isEmpty)
  }

  def removeEntry(entryName:String): Directory = {
    if (!hasEntry(entryName)) this
    else
      new Directory(parentPath, name, contents.filter(x => {!x.name.equals(entryName)}))
  }

  def hasEntry(name: String) : Boolean = findEntry(name) != null

  def findDescendants(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendants(path.tail)
  }

  def findDescendants(relativePath: String): Directory = {
    if (relativePath.isEmpty) this
    else findDescendants(relativePath.split(Directory.SEPARATOR).toList)
  }

  def asDirectory: Directory = this

  def asFile: File = throw new FileSystemExceptions("Directory cannot be converted to file")

  def isRoot: Boolean = parentPath.isEmpty

  def isFile:Boolean = false

  def isDirectory:Boolean = true
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT = Directory.empty("","")

  def apply(parentPath: String, name: String, contents: List[DirEntry]): Directory={
    new Directory(parentPath, name, contents)
  }

  def empty(parentPath:String, name: String): Directory = {
    new Directory(parentPath, name, List())
  }

}
