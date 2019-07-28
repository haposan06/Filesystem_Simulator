package com.jhn.scala.commands

import com.jhn.scala.files.{DirEntry, Directory}
import com.jhn.scala.filesystem.State

class MkdirCommand(name:String) extends Command {
  def apply(state: State): State = {
    val workingDir = state.workingDir
    if (workingDir.hasEntry(name)) state.setMessage(s"Entry $name has already exist within this directory")
    else if (name.contains(Directory.SEPARATOR)) state.setMessage(s"$name cannot be separator")
    else if (checkIllegal(name)) state.setMessage(s"$name: illegal entry name!")
    else {
      doMkdir(state, name)
    }
  }

  def checkIllegal(str: String):Boolean = {
    name.contains(".")
  }

  private def doMkdir(state: State, name: String): State = {
    def updateStructure(currentDir: Directory, path: List[String], newEntry: DirEntry): Directory= {
      if (path.isEmpty) currentDir.addEntry(newEntry)
      else {
        val oldEntry = currentDir.findEntry(path.head).asDirectory
        currentDir.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }
    val wd = state.workingDir

    val allDirsInPath = wd.getAllFoldersInPath
    val newDir = Directory.empty(wd.path, name)

    val newRoot = updateStructure(state.root, allDirsInPath, newDir)
    val newWd = newRoot.findDescendants(allDirsInPath)
    State(newRoot, newWd)
  }

}
