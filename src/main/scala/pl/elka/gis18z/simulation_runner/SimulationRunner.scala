package pl.elka.gis18z.simulation_runner

import java.util.concurrent.{TimeUnit, TimeoutException}

import pl.elka.gis18z.algorithm.UnRootedTreeIsomorphismAlgorithm
import pl.elka.gis18z.config.AppConfig

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class SimulationRunner(config: AppConfig) {

  private val WARMUP_JVM_SECONDS = 0.005 // FIXME developersko mniejszy czas
  private val INFO_EVERY_NTH_PERCENT = 10

  def run(): SimulationResult = {
    println("Generating problem instances ...")
    val problemInstances = new ProblemGenerator(config).generate()
    println("Problem instances generated.")

    println("Warming up JVM for " + WARMUP_JVM_SECONDS + " seconds")
    warmupJVM(problemInstances)
    println("Done warming up JVM")

    val res: scala.collection.mutable.Map[ProblemSize, ProblemSolutionResult] = scala.collection.mutable.Map.empty

    val repeat = config.r
    var areIsomorphic: Boolean = false

    println("Running " + problemInstances.length + " instances, from n = " + config.n1 + " to n = " + config.n2 + " and from d = " + config.d1 + " to d = " + config.d2 + "...")
    for(p <- problemInstances) {
      print("\r")
      print("Running for n = " + p.n + ", d = " + p.depth)
      val times = (for(i <- 0 to repeat) yield {

        val start = System.currentTimeMillis()
        areIsomorphic = UnRootedTreeIsomorphismAlgorithm.areIsomorphic(p.tree1, p.tree2).areIsomorphic
        val stop = System.currentTimeMillis()

        stop - start
      }).toList

      val avgTime = (times.sum.toFloat / times.length.toFloat).toLong

      res.put(ProblemSize(p.n, p.depth), ProblemSolutionResult(avgTime, areIsomorphic))
    }
    print("\r")
    print("Done running all instances.\n")

    SimulationResult(res.toMap)
  }


  def warmupJVM(problemInstances: List[ProblemInstance]) = {
    def run(): Unit = {
      while(true) {
        for(p <- problemInstances) {
          UnRootedTreeIsomorphismAlgorithm.areIsomorphic(p.tree1, p.tree2)
        }
      }
    }

    implicit val ec = ExecutionContext.global
    try {
      Await.result(Future(run), Duration(WARMUP_JVM_SECONDS, TimeUnit.SECONDS)).asInstanceOf[Option[Unit]]
    } catch {
      case e: TimeoutException =>
      // nothing
    }

    // done warming up
  }
}
