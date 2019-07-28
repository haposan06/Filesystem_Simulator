package com.jhn.scala.commands
import com.jhn.scala.files.{DirEntry, Directory}
import com.jhn.scala.filesystem.State

class CdCommand(dir:String) extends Command{
  def apply(state: State): State = {
    val root = state.root
    val wd = state.workingDir
    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    val destinationDir = doFindEntry(root, absolutePath)

    if (destinationDir == null || !destinationDir.isDirectory)
      state.setMessage(s"$dir: No such directory")
    else State(root, destinationDir.asDirectory)
  }

  def doFindEntry(root: Directory, path: String):DirEntry = {
    def findEntryHelper(currentDir: Directory, path: List[String]):DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDir // return root /
      else if (path.tail.isEmpty) currentDir.findEntry(path.head)
      else {
        val nextDir = currentDir.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    def collapseRelativeTokens(path:List[String], result:List[String]) : List[String] = {
      if (path.isEmpty) result
      else if (".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if("..".equals(path.head)) {
        if (result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init)
      }
      else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList

    val newTokens = collapseRelativeTokens(tokens, List())
    if (newTokens == null) null
    else findEntryHelper(root, newTokens)
  }
}
