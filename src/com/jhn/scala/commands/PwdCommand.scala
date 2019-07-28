package com.jhn.scala.commands
import com.jhn.scala.filesystem.State

class PwdCommand extends Command {
  def apply(state: State):State = {
     state.setMessage(state.workingDir.path)
  }
}
