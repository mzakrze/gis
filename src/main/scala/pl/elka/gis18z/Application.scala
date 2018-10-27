package pl.elka.gis18z

import org.rogach.scallop.exceptions.RequiredOptionNotFound
import pl.elka.gis18z.config.{ConfigParser, InvalidConfigurationException}
import pl.elka.gis18z.io.SolutionInterpreter
import pl.elka.gis18z.simulation_runner.{SimulationResult, SimulationRunner}

object Application extends App {

  override def main(args: Array[String]): Unit = {

    try {

      val config = new ConfigParser(args).parse()

      val simulationResult: SimulationResult = new SimulationRunner(config).run()

      new SolutionInterpreter(config).interpret(simulationResult)

    } catch  {
      case RequiredOptionNotFound(optionName) =>
        // swallow exception
    }
  }

}
