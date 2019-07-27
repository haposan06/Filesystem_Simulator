package com.jhn.scala.commands

import com.jhn.scala.filesystem.State

trait Command {
  def apply(state:State): State
}

object Command {
  def from(input:String): Command =
    new UnknowCommand
}