package com.jhn.scala.commands

import com.jhn.scala.files.{DirEntry, Directory}
import com.jhn.scala.filesystem.State

abstract class CreateEntry(entryName:String) extends Command {

  def apply(state: State): State = {
    val workingDir = state.workingDir
    if (workingDir.hasEntry(entryName)) state.setMessage(s"Entry $entryName has already exist within this directory")
    else if (entryName.contains(Directory.SEPARATOR)) state.setMessage(s"$entryName cannot be separator")
    else if (checkIllegal(entryName)) state.setMessage(s"$entryName: illegal entry name!")
    else {
      doCreateEntry(state, entryName)
    }
  }

  def checkIllegal(str: String):Boolean = {
    entryName.contains(".")
  }

  private def doCreateEntry(state: State, name: String): State = {
    def updateStructure(currentDir: Directory, path: List[String], newEntry: DirEntry): Directory= {
      if (path.isEmpty) currentDir.addEntry(newEntry)
      else {
        val oldEntry = currentDir.findEntry(path.head).asDirectory
        currentDir.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }
    val wd = state.workingDir

    val allDirsInPath = wd.getAllFoldersInPath

    val newEntry: DirEntry = createSpecificEntry(state)
//    val newDir = Directory.empty(wd.path, name)

    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    val newWd = newRoot.findDescendants(allDirsInPath)
    State(newRoot, newWd)
  }
  def createSpecificEntry(state:State): DirEntry

}
