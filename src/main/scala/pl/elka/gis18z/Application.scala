package pl.elka.gis18z

import org.rogach.scallop.exceptions.RequiredOptionNotFound
import pl.elka.gis18z.config.{ConfigParser, InvalidConfigurationException}
import pl.elka.gis18z.io.SolutionInterpreter
import pl.elka.gis18z.simulation_runner.{SimulationResult, SimulationRunner}

object Application extends App {

  /*
  TODO:
  - algorytm może też działać szybiej/wolniej w zależności od tego czy dla danego problemu izomorfizm jest czy nie ma
  - można też dodać flagę, która wygeneruje tylko przypadki pozytywne, albo tylko negatywne
   */

  override def main(args: Array[String]): Unit = {

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
