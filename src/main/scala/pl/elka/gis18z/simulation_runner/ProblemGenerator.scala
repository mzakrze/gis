package pl.elka.gis18z.simulation_runner

import pl.elka.gis18z.algorithm.{UnRootedTree, UndirectedEdge, Vertice}
import pl.elka.gis18z.config.{AppConfig, InvalidConfigurationException}

import scala.collection.mutable
import scala.util.Random


/**
  * Generuje tylko nietrywialne przypadki, tzn
  * drzewa spełniające warunki konieczne, aby były izomorficzne(których sprawdzenie jest trywialne):
  * - tyle samo wierzchołków
  * @param appConfig
  */
class ProblemGenerator(appConfig: AppConfig) {

  def generate(): List[ProblemInstance] = {

    val verticesNoSeq = generateSteps(appConfig.n1, appConfig.n2, appConfig.ns)
    val treeDepthSeq = generateSteps(appConfig.d1, appConfig.d2, appConfig.ds)

    val randomSeed = Random

    val problems =
      for (n <- verticesNoSeq) yield {
        for(depth <- treeDepthSeq)
          yield
            if(n <= depth) {
              // TODO generate warnign
              null
            } else {
               ProblemInstance(n, depth, generateSingleRandomUnRootedTree(n, depth, randomSeed), generateSingleRandomUnRootedTree(n, depth, randomSeed))
            }
      }

    problems.flatten.filter(p => p != null)
      .sortWith((p1, p2) => if (p1.depth != p2.depth) p1.depth < p2.depth else p1.n < p2.n)
  }

  private def generateSteps(lower: Int, upper: Int, stepsNo: Int): List[Int] = {
    val step: Float = (upper - lower).toFloat / stepsNo.toFloat

    (for(s <- 1 to stepsNo) yield (lower + s * step).toInt).toList
  }

  private def generateSingleRandomUnRootedTree(n: Int, depth: Int, randomSeed: Random): UnRootedTree = {

    val verticeIdToDepthMap: scala.collection.mutable.Map[Int, Int] = mutable.Map.empty

    var edges: List[UndirectedEdge] = List.empty
    val vertices: List[Vertice] = randomSeed.shuffle(1 to n).map(Vertice).toList

    var legalVertices: Set[Vertice] = vertices.toSet

    // 1. skonstruuj ścieżkę, będąca maksymalną głębokością
    val root = legalVertices.head
    legalVertices = legalVertices.tail

    var child = legalVertices.head
    legalVertices = legalVertices.tail

    edges = UndirectedEdge(root, child) :: edges
    verticeIdToDepthMap.put(root.id, 0)
    verticeIdToDepthMap.put(child.id, 1)

    for(d <- 2 to depth) {
      var parent = child
      child = legalVertices.head
      legalVertices = legalVertices.tail

      verticeIdToDepthMap.put(child.id, d)
      edges = UndirectedEdge(parent, child) :: edges
    }

    // 2. dokładaj losowe wierzchołki, spośród pozostałych, w losowe miejsce tak, aby nie przekroczyć maksymalnej głębokości
    for(vertToBeAdded <- legalVertices) {
      var anchor: Vertice = null
      var willNotExceedMaxDepth = false
      while(willNotExceedMaxDepth == false) {
        anchor = edges(randomSeed.nextInt(edges.size - 1)).v1
        if(verticeIdToDepthMap(anchor.id) < depth) {
          willNotExceedMaxDepth = true
        }
      }
      verticeIdToDepthMap.put(vertToBeAdded.id, verticeIdToDepthMap(anchor.id) + 1)
      edges = UndirectedEdge(anchor, vertToBeAdded) :: edges
    }

    UnRootedTree(vertices, edges)
  }

}
