package com.jhn.scala.commands

import com.jhn.scala.filesystem.State

trait Command {
  def apply(state:State): State
}

object Command {
  val MKDIR_CMD:String = "mkdir"
  val LS_CMD:String = "ls"
  val PWD_CMD: String = "pwd"
  val TOUCH_CMD: String = "touch"
  val CD_CMD:String = "cd"
  val RM_CMD:String = "rm"

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
    else if (PWD_CMD.equals(tokens(0))) new PwdCommand
    else if (TOUCH_CMD.equals(tokens(0))) new TouchCommand(tokens(1))
    else if (CD_CMD.equals(tokens(0)))  new CdCommand(tokens(1))
    else if (RM_CMD.equals(tokens(0)))  new RmCommand(tokens(1))
    else new UnknowCommand
  }
}


