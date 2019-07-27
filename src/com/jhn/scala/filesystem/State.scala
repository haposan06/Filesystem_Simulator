package com.jhn.scala.filesystem

import com.jhn.scala.files.Directory

class State(val root:Directory, val workingDir: Directory, val output: String) {
  def show:Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }
  def setMessage(message:String): State =
    State(root, workingDir, message)
}

object State {
  val SHELL_TOKEN = "$ "
  def apply(root:Directory, workingDir:Directory, output:String = "") : State ={
    new State(root, workingDir, output)
  }
}