package pl.elka.gis18z.simulation_runner

import pl.elka.gis18z.algorithm.{UnRootedTree, UndirectedEdge, Vertice}
import pl.elka.gis18z.config.AppConfig

import scala.util.Random

import scala.util.control.Breaks._


/**
  * Generuje tylko nietrywialne przypadki, tzn
  * drzewa spełniające warunki konieczne, aby były izomorficzne(których sprawdzenie jest trywialne):
  * - tyle samo wierzchołków
  * - taki sam rozkład stopni wierzchołków
  * @param appConfig
  */
class ProblemGenerator(appConfig: AppConfig) {
  def generate(): List[ProblemInstance] = {

    val verticesNoSeq = List.range(appConfig.n1, appConfig.n2 + 1, appConfig.s)
    val avgDepthSeq = arr(appConfig.d1, appConfig.d2, appConfig.s) // FIXME - generować rozkład stopni, nie avg

    val randomSeed = Random

    (verticesNoSeq zip avgDepthSeq).map {
      case (n, depth) =>
        ProblemInstance(generateSingleRandomUnRootedTree(n, depth, randomSeed), generateSingleRandomUnRootedTree(n, depth, randomSeed))
    }
  }


  def generateSingleRandomUnRootedTree(n: Int, depth: Int, randomSeed: Random): UnRootedTree = {
    // n wierzchołków i głębokość d -> jaki stopień wierzchołka?
    val degree = Math.pow(n , 1f / depth.toFloat).ceil.toInt

    var edges: List[UndirectedEdge] = List.empty

    val vertices: List[Vertice] = randomSeed.shuffle(1 to n).map(Vertice).toList
    var legalVertices: Set[Vertice] = vertices.toSet

    def appendChildren(v: Vertice): Unit = {
      var neighbours: List[Vertice] = List.empty

      breakable {
        for(c <- 1 to degree) {
          if(legalVertices.isEmpty){
            break
          }
          val random = randomSeed.nextInt(legalVertices.size)
          val neighbour = legalVertices.slice(random, random + 1).last // losowy wierzchołek
          neighbours = neighbour :: neighbours
          legalVertices -= neighbour

          edges = UndirectedEdge(v, neighbour) :: edges
        }
      }

      neighbours.foreach(appendChildren)
    }
    val someRndVertice = legalVertices.head
    legalVertices -= someRndVertice
    appendChildren(someRndVertice)

    UnRootedTree(vertices, edges)
  }

  def arr(from: Int, to: Int, stepsNo: Int): List[Int] = {

    val random = Random

    val step = (to - from).toFloat / stepsNo.toFloat

    (1 to stepsNo).map(s => {
      val lower = (from + step * s).toInt
      val upper = (to + step * s).toInt
      random.nextInt(upper - lower + 1) + lower
    }).toList
  }
}
