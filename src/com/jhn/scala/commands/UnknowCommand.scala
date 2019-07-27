package com.jhn.scala.commands

import com.jhn.scala.filesystem.State

class UnknowCommand extends Command {
  def apply(state: State) :State = {
    state.setMessage("Command not found!")
  }
}
