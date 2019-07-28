package com.jhn.scala.commands
import com.jhn.scala.files.{DirEntry, File}
import com.jhn.scala.filesystem.State

class TouchCommand(name:String) extends CreateEntry(name:String) {
  def createSpecificEntry(state: State): DirEntry = {
    File.empty(state.workingDir.path, name)
  }
}
