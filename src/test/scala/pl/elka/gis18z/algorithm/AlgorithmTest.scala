package pl.elka.gis18z.algorithm

import java.util.concurrent.{TimeUnit, TimeoutException}

import org.jgrapht.Graph
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector
import org.jgrapht.graph.{DefaultEdge, DefaultUndirectedGraph}
import org.scalatest.{FlatSpec, Matchers}
import pl.elka.gis18z.config.AppConfig
import pl.elka.gis18z.simulation_runner.ProblemGenerator

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

class AlgorithmTest extends FlatSpec with Matchers {

  "An algorithm" should "work as a reference solver for random cases" in {

    val n1 = 10
    val n2 = 40
    val ns = 10
    val d1 = 3
    val d2 = 20
    val ds = 10

    val appConfig = AppConfig(n1, n2, ns, d1, d2, ds, 0, null)

    val problemInstances = new ProblemGenerator(appConfig).generate()

    for(p <- problemInstances){

      testWithReferenceSolver(p.tree1, p.tree2)

    }
  }

  "An algorithm" should "work for positive cases" in {

    val n1 = 10
    val n2 = 40
    val ns = 10
    val d1 = 3
    val d2 = 20
    val ds = 10

    val appConfig = AppConfig(n1, n2, ns, d1, d2, ds, 0, null)

    val problemInstances = new ProblemGenerator(appConfig).generate()

    for(p <- problemInstances){

      UnRootedTreeIsomorphismAlgorithm.areIsomorphic(p.tree1, p.tree1).areIsomorphic shouldBe true

      val tree1Copy = UnRootedTree(scala.util.Random.shuffle(p.tree1.vertices), scala.util.Random.shuffle(p.tree1.edges))

      UnRootedTreeIsomorphismAlgorithm.areIsomorphic(p.tree1, tree1Copy).areIsomorphic shouldBe true
    }
  }

  "An algorithm" should "work with defined trees" in {
    val tree1 = UnRootedTree(List(Vertice(7), Vertice(4), Vertice(1), Vertice(6), Vertice(10), Vertice(12), Vertice(9), Vertice(11), Vertice(13), Vertice(3), Vertice(8), Vertice(5), Vertice(2)),List(UndirectedEdge(Vertice(12),Vertice(2)), UndirectedEdge(Vertice(8),Vertice(9)), UndirectedEdge(Vertice(5),Vertice(8)), UndirectedEdge(Vertice(3),Vertice(5)), UndirectedEdge(Vertice(12),Vertice(3)), UndirectedEdge(Vertice(11),Vertice(12)), UndirectedEdge(Vertice(13),Vertice(11)), UndirectedEdge(Vertice(1),Vertice(13)), UndirectedEdge(Vertice(7),Vertice(1)), UndirectedEdge(Vertice(6),Vertice(7)), UndirectedEdge(Vertice(4),Vertice(6)), UndirectedEdge(Vertice(10),Vertice(4))))

    val tree2 = UnRootedTree(List(Vertice(1), Vertice(2), Vertice(8), Vertice(13), Vertice(12), Vertice(5), Vertice(9), Vertice(4), Vertice(6), Vertice(7), Vertice(3), Vertice(10), Vertice(11)),List(UndirectedEdge(Vertice(1),Vertice(2)), UndirectedEdge(Vertice(8),Vertice(9)), UndirectedEdge(Vertice(5),Vertice(8)), UndirectedEdge(Vertice(3),Vertice(5)), UndirectedEdge(Vertice(12),Vertice(3)), UndirectedEdge(Vertice(11),Vertice(12)), UndirectedEdge(Vertice(13),Vertice(11)), UndirectedEdge(Vertice(1),Vertice(13)), UndirectedEdge(Vertice(7),Vertice(1)), UndirectedEdge(Vertice(6),Vertice(7)), UndirectedEdge(Vertice(4),Vertice(6)), UndirectedEdge(Vertice(10),Vertice(4))))


    UnRootedTreeIsomorphismAlgorithm.areIsomorphic(tree1, tree2).areIsomorphic shouldBe true
  }

  def testWithReferenceSolver(tree1: UnRootedTree, tree2: UnRootedTree): Unit = {

    val graph1 = transform(tree1)
    val graph2 = transform(tree2)

    implicit val ec = ExecutionContext.global
    try {
      val referenceSolution = Await.result(Future(new VF2GraphIsomorphismInspector(graph1, graph2).isomorphismExists()), Duration(2.0, TimeUnit.SECONDS))

      val solution = UnRootedTreeIsomorphismAlgorithm.areIsomorphic(tree1, tree2)

      solution.areIsomorphic shouldBe referenceSolution
    } catch {
      case e: TimeoutException =>
        // jgrapht has frozen, ignore that test-case
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
