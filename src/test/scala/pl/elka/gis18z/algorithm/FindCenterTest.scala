package pl.elka.gis18z.algorithm

import org.scalatest._
import pl.elka.gis18z.algorithm.findCenter.FindCenterAlgorithm

class FindCenterTest extends FlatSpec with Matchers {

  "Function" should "return " in {
    val unRootedTree = UnRootedTree(
      List(Vertice(1),Vertice(2),Vertice(3),Vertice(4),Vertice(5),Vertice(6),Vertice(7),Vertice(8),Vertice(9),Vertice(10),
        Vertice(11),Vertice(12),Vertice(13),Vertice(14),Vertice(15),Vertice(16),Vertice(17),Vertice(18),Vertice(19)),
      List(UndirectedEdge(Vertice(1), Vertice(5)),UndirectedEdge(Vertice(4), Vertice(5)), UndirectedEdge(Vertice(2), Vertice(3)),
        UndirectedEdge(Vertice(2), Vertice(6)), UndirectedEdge(Vertice(5), Vertice(6)), UndirectedEdge(Vertice(6), Vertice(7)),
        UndirectedEdge(Vertice(6), Vertice(8)), UndirectedEdge(Vertice(6), Vertice(9)), UndirectedEdge(Vertice(6), Vertice(10)),
        UndirectedEdge(Vertice(10), Vertice(11)), UndirectedEdge(Vertice(9), Vertice(12)), UndirectedEdge(Vertice(9), Vertice(13)),
        UndirectedEdge(Vertice(9), Vertice(14)), UndirectedEdge(Vertice(5), Vertice(15)), UndirectedEdge(Vertice(16), Vertice(15)),
        UndirectedEdge(Vertice(15), Vertice(17)), UndirectedEdge(Vertice(17), Vertice(18)), UndirectedEdge(Vertice(17), Vertice(19)))
    )

    FindCenterAlgorithm.findCenter(unRootedTree).map(_.id) should be (Set(5))
  }
}
