package com.jhn.scala.commands
import com.jhn.scala.files.DirEntry
import com.jhn.scala.filesystem.State

class LsCommand extends Command {
  def apply(state: State):State = {
    val wd = state.workingDir
    val contents = wd.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }

  def createNiceOutput(contents: List[DirEntry]): String = {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head
      s"${entry.name}[${entry.getType}]\n" + createNiceOutput(contents.tail)
    }
  }
}
