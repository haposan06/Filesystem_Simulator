package com.jhn.scala.commands

import com.jhn.scala.files.{DirEntry, Directory}
import com.jhn.scala.filesystem.State

class MkdirCommand(name:String) extends CreateEntry(name:String) {
  def createSpecificEntry(state: State): DirEntry = {
    Directory.empty(state.workingDir.path, name)
  }
}
