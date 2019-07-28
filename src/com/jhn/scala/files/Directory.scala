package com.jhn.scala.files

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {
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

  def hasEntry(name: String) : Boolean = findEntry(name) != null

  def findDescendants(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendants(path.tail)
  }

  def asDirectory: Directory = this

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
