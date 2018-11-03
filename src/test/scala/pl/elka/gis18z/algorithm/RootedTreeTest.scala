package pl.elka.gis18z.algorithm

import org.scalatest._

class RootedTreeTest extends FlatSpec with Matchers {

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

    RootedTree.createFromUnRootedTree(unRootedTree, Vertice(5)) should be (RootedTree(
      Branch(5, Vertice(-1), List(
          Leaf(1, Vertice(5)),
          Leaf(4, Vertice(5)),
          Branch(6, Vertice(5), List(
            Branch(2, Vertice(6), List(
              Leaf(3, Vertice(2))
            )),
            Leaf(7, Vertice(6)),
            Leaf(8, Vertice(6)),
            Branch(9, Vertice(6), List(
              Leaf(12, Vertice(9)),
              Leaf(13, Vertice(9)),
              Leaf(14, Vertice(9))
            )),
            Branch(10, Vertice(6), List(
              Leaf(11, Vertice(10))
            )))),
        Branch(15, Vertice(5), List(
          Leaf(16, Vertice(15)),
          Branch(17, Vertice(15), List(
            Leaf(18, Vertice(17)),
            Leaf(19, Vertice(17))))))
        ))))
  }
}
