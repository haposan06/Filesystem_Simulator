package com.jhn.scala.commands

import com.jhn.scala.filesystem.State

trait Command {
  def apply(state:State): State
}

object Command {
  val MKDIR_CMD:String = "mkdir"
  val LS_CMD:String = "ls"

  def emptyCommand: Command = new Command {
    def apply(state: State) = state
  }

  def incompleteCommand(input:String): Command = new Command {
    def apply(state: State) = state.setMessage(s"$input is incomplete command!")
  }
  def from(input:String): Command = {
    val tokens = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR_CMD.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR_CMD)
      else new MkdirCommand(tokens(1))
    }
    else if (LS_CMD.equals(tokens(0))) new LsCommand
    else new UnknowCommand
  }
}


