package pl.elka.gis18z.io

import java.io.{BufferedWriter, FileWriter}

import pl.elka.gis18z.config.AppConfig
import pl.elka.gis18z.simulation_runner.SimulationResult

class SolutionInterpreter(config: AppConfig) {

  def interpret(res: SimulationResult) = {
    println("Dumping results...")

    val fileContent = getFileContent(res)

    val folderPath = writeToFile(fileContent)

    runRScriptSync()

    println("Results dumped to: " + folderPath)
  }

  def getFileContent(res: SimulationResult): String = {
    val sep = ";"
    val headers = List("Number of vertices", "Depth of tree", "Are isomorphic", "Time [ms]")
    type Row = (Int, Int, Boolean, Int)

    val rows = for((problemSize, result) <- res.problemSizeToSolution) yield
      (problemSize.n, problemSize.depth, result.areIsomorphic, result.time)

    val header = headers.mkString(sep) + "\n"
    val body = rows.map(row => row._1 + sep + row._2 + sep + row._3 + sep + row._4).mkString("\n")

    header + body
  }

  def writeToFile(fileContent: String): String = {
    val folder = new java.io.File(config.out)
    folder.delete()
    folder.mkdir()
    val file = new java.io.File(folder.getPath() + "/result.csv")
    file.delete()
    file.createNewFile()
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(fileContent)
    bw.close()
    folder.getAbsolutePath
  }

  def runRScriptSync(): Unit = {
    try {
      val pb = new ProcessBuilder("/bin/bash", "runR.sh")
      pb.inheritIO()
      val p = pb.start
      p.waitFor
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

}
