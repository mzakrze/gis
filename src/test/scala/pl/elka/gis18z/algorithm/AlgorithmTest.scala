package pl.elka.gis18z.algorithm

import org.jgrapht.Graph
import org.jgrapht.alg.isomorphism.{VF2GraphIsomorphismInspector}
import org.jgrapht.graph.{DefaultEdge, DefaultUndirectedGraph}
import org.scalatest.FlatSpec
import pl.elka.gis18z.config.AppConfig
import pl.elka.gis18z.simulation_runner.ProblemGenerator

class AlgorithmTest extends FlatSpec {

  "An algorithm" should "work" in {

    val n1 = 10
    val n2 = 40
    val d1 = 3
    val d2 = 20
    val s = 10

    val appConfig = AppConfig(n1, n2, d1, d2, s, 0, null)

    val problemInstances = new ProblemGenerator(appConfig).generate()

    for(p <- problemInstances){

      runSingleTest(p.tree1, p.tree2)

    }
  }

  def runSingleTest(tree1: UnRootedTree, tree2: UnRootedTree): Unit = {

    val graph1 = transform(tree1)
    val graph2 = transform(tree2)

    val referenceSolution = new VF2GraphIsomorphismInspector(graph1, graph2)

    val solution = UnRootedTreeIsomorphismAlgorithm.areIsomorphic(tree1, tree2)

    if(referenceSolution.isomorphismExists() == solution.areIsomorphic) {
      println("Algorithm ok")
    } else {
      println("Algorithm FAILED !!!")
    }
  }

  def transform(tree: UnRootedTree): Graph[Int, DefaultEdge] = {
    val graph = new DefaultUndirectedGraph[Int, DefaultEdge](classOf[DefaultEdge])
    for(v <- tree.vertices) {
      graph.addVertex(v.id)
    }
    for(e <- tree.edges) {
      graph.addEdge(e.v1.id, e.v2.id)
    }
    graph
  }

}
