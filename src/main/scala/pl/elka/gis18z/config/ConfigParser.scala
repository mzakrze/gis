package pl.elka.gis18z.config

import org.rogach.scallop.ScallopConf
import org.rogach.scallop.exceptions._

class ConfigParser(args: Array[String]) extends ScallopConf(args) {

  version("GIS -  0.0.1 (c) 2018 Mariusz Zakrzewski, Szymon Borodziuk")

  banner("Run simulation of algorithm checking for unrooted trees isomorphism.\n\nUsage:")

  val n1 = opt[Int]("n1", descr = "Initial number of vertices for both trees", required = true)
  val n2 = opt[Int]("n2", descr = "Final number of vertices for both trees", required = true)
  val d1 = opt[Int]("d1", descr = "Initial depth for both trees", required = true)
  val d2 = opt[Int]("d2", descr = "Final depth for both trees", required = true)
  val s = opt[Int]("steps", descr = "Number of steps between initial and final size of the problem", required = true)
  val r = opt[Int]("repeat", descr = "How many times to repeat each experiment per problem size", required = true)
  val out = opt[String]("output", descr = "Folder name to dump all data",default = null, required = false)

  verify()

  override def onError(e: Throwable): Unit = e match {
    case RequiredOptionNotFound(optionName) =>
      println("Error: argument " + optionName + " not provided.\n")
      printHelp()
      throw e
  }

  def parse(): AppConfig = {

    var outArg: String = null
    if(out.supplied) {
      outArg = out()
    }

    AppConfig(n1(), n2(), d1(), d2(), s(), r(), outArg)

  }
}