package com.jhn.scala.commands
import com.jhn.scala.files.Directory
import com.jhn.scala.filesystem.State

class RmCommand(name:String) extends Command {
  def apply(state: State): State = {
    val wd = state.workingDir
    val absolutePath =
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (wd.isRoot) wd.path + name
      else wd.path + Directory.SEPARATOR + name

    if (Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("Cannot remove root folder")
    else
      doRm(state, absolutePath)
  }

  def doRm(state: State, path: String):State = {
    def rmHelper(currentDir: Directory, path: List[String]):Directory = {
      if (path.isEmpty) currentDir
      else if (path.tail.isEmpty) currentDir.removeEntry(path.head)
      else {
        val nextDirectory = currentDir.findEntry(path.head)
        if (!nextDirectory.isDirectory) currentDir
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if (newNextDirectory == nextDirectory) currentDir
          currentDir.replaceEntry(path.head, newNextDirectory)
        }
      }
    }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot:Directory = rmHelper(state.root, tokens)

    if (newRoot == state.root)
      state.setMessage(path + ":no such file or directory")
    else
      State(newRoot, newRoot.findDescendants(state.workingDir.path.substring(1)))
  }
}
