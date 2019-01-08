package pl.elka.gis18z

import java.util.Calendar

import org.rogach.scallop.exceptions.RequiredOptionNotFound
import pl.elka.gis18z.config.{AppConfig, ConfigParser, InvalidConfigurationException}
import pl.elka.gis18z.io.SolutionInterpreter
import pl.elka.gis18z.simulation_runner.{SimulationResult, SimulationRunner}

object Application extends App {

  override def main(args: Array[String]): Unit = {

      val startTime = Calendar.getInstance().getTime()
      println("Started at: " + startTime.toString)

      doRun(args)

      val stopTime = Calendar.getInstance().getTime()
      println("Finished at: " + stopTime.toString)

      val totalTime = ((stopTime.getTime - startTime.getTime) / 1000.0 / 60.0) + " minutes"
      println("Total time: " + totalTime)
  }

  def doRun(args: Array[String]) = {

    try {

      val config = new ConfigParser(args).parse()

      val simulationResult: SimulationResult = new SimulationRunner(config).run()

      new SolutionInterpreter(config).interpret(simulationResult)

    } catch  {
      case InvalidConfigurationException(msg) =>
        println(msg)
      case RequiredOptionNotFound(optionName) =>
        // swallow exception
    }
  }

}
