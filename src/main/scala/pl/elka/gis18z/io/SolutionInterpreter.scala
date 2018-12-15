package pl.elka.gis18z.io

import java.io.{BufferedWriter, FileWriter}

import pl.elka.gis18z.config.AppConfig
import pl.elka.gis18z.simulation_runner.SimulationResult

class SolutionInterpreter(config: AppConfig) {

  // TODO - można pliki gdzie indziej umieścic

  def interpret(res: SimulationResult) = {

    val fileContent = getFileContent(res)

    writeToFile(fileContent)

    runRScriptSync()
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

  def writeToFile(fileContent: String): Unit = {
    // TODO - info gdzie jest plik itp
    val f = new java.io.File(config.out)
    f.delete()
    f.mkdir()
    val file = new java.io.File(f.getPath() + "/result.csv")
    file.delete()
    file.createNewFile()
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(fileContent)
    bw.close()
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
