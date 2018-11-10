package pl.elka.gis18z.simulation_runner

import java.util.concurrent.{TimeUnit, TimeoutException}

import pl.elka.gis18z.algorithm.UnRootedTreeIsomorphismAlgorithm
import pl.elka.gis18z.config.AppConfig

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class SimulationRunner(config: AppConfig) {

  private val WARMUP_JVM_SECONDS = 0.005 // FIXME developersko mniejszy czas

  def run(): SimulationResult = {
    val problemInstances = new ProblemGenerator(config).generate()

    warmupJVM(problemInstances)

    val res: scala.collection.mutable.Map[ProblemSize, ProblemSolutionResult] = scala.collection.mutable.Map.empty

    val repeat = config.r
    var areIsomorphic: Boolean = false
    for(p <- problemInstances) {
      val times = (for(i <- 0 to repeat) yield {

        val start = System.nanoTime()
        areIsomorphic = UnRootedTreeIsomorphismAlgorithm.areIsomorphic(p.tree1, p.tree2).areIsomorphic
        val stop = System.nanoTime()

        stop - start
      }).toList

      val avgTime = (times.sum.toFloat / times.length.toFloat).toLong

      res.put(ProblemSize(p.n, p.depth), ProblemSolutionResult(avgTime, areIsomorphic))
    }

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
