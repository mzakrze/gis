package pl.elka.gis18z.algorithm

import org.scalatest._
import pl.elka.gis18z.algorithm.rootedisomorphic.{Node, RootedTree}

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

    RootedTree.createFromUnRootedTree(unRootedTree, Vertice(5), 0) should be (RootedTree(
      Node.withChildren(5, Vertice(-1), List(
        Node(1, Vertice(5)),
        Node(4, Vertice(5)),
        Node.withChildren(6, Vertice(5), List(
          Node.withChildren(2, Vertice(6), List(
            Node(3, Vertice(2))
            )),
          Node(7, Vertice(6)),
          Node(8, Vertice(6)),
          Node.withChildren(9, Vertice(6), List(
            Node(12, Vertice(9)),
            Node(13, Vertice(9)),
            Node(14, Vertice(9))
            )),
          Node.withChildren(10, Vertice(6), List(
            Node(11, Vertice(10))
            )))),
        Node.withChildren(15, Vertice(5), List(
          Node(16, Vertice(15)),
          Node.withChildren(17, Vertice(15), List(
            Node(18, Vertice(17)),
            Node(19, Vertice(17))))))
        ))))

    val unRootedTree1 = UnRootedTree(
      List(Vertice(1),Vertice(2),Vertice(3),Vertice(4),Vertice(5),Vertice(6),Vertice(7),Vertice(8),Vertice(9),Vertice(10),
        Vertice(11),Vertice(12),Vertice(13),Vertice(14),Vertice(15)),
      List(UndirectedEdge(Vertice(4), Vertice(10)),UndirectedEdge(Vertice(4), Vertice(11)), UndirectedEdge(Vertice(4), Vertice(6)),
        UndirectedEdge(Vertice(10), Vertice(9)), UndirectedEdge(Vertice(10), Vertice(8)), UndirectedEdge(Vertice(10), Vertice(14)),
        UndirectedEdge(Vertice(10), Vertice(15)), UndirectedEdge(Vertice(10), Vertice(1)), UndirectedEdge(Vertice(10), Vertice(2)),
        UndirectedEdge(Vertice(10), Vertice(7)), UndirectedEdge(Vertice(10), Vertice(5)), UndirectedEdge(Vertice(10), Vertice(3)),
        UndirectedEdge(Vertice(10), Vertice(12)), UndirectedEdge(Vertice(10), Vertice(13)))
    )

    RootedTree.createFromUnRootedTree(unRootedTree1, Vertice(10), 0) should be (RootedTree(
      Node.withChildren(10, Vertice(-1), List(
        Node.withChildren(4, Vertice(10), List(
          Node(11, Vertice(4)),
          Node(6, Vertice(4))
        )),
        Node(9, Vertice(10)),
        Node(8, Vertice(10)),
        Node(14, Vertice(10)),
        Node(15, Vertice(10)),
        Node(1, Vertice(10)),
        Node(2, Vertice(10)),
        Node(7, Vertice(10)),
        Node(5, Vertice(10)),
        Node(3, Vertice(10)),
        Node(12, Vertice(10)),
        Node(13, Vertice(10))
      ))))

    val unRootedTree2 = UnRootedTree(
      List(Vertice(1),Vertice(2),Vertice(3),Vertice(4),Vertice(5),Vertice(6),Vertice(7),Vertice(8),Vertice(9),Vertice(10),
        Vertice(11),Vertice(12),Vertice(13),Vertice(14),Vertice(15),Vertice(16),Vertice(17),Vertice(18),Vertice(19)),
      List(UndirectedEdge(Vertice(17), Vertice(2)),UndirectedEdge(Vertice(17), Vertice(19)), UndirectedEdge(Vertice(4), Vertice(17)),
        UndirectedEdge(Vertice(10), Vertice(17)), UndirectedEdge(Vertice(10), Vertice(16)), UndirectedEdge(Vertice(10), Vertice(14)),
        UndirectedEdge(Vertice(10), Vertice(5)), UndirectedEdge(Vertice(4), Vertice(3)), UndirectedEdge(Vertice(4), Vertice(6)),
        UndirectedEdge(Vertice(6), Vertice(15)), UndirectedEdge(Vertice(6), Vertice(7)), UndirectedEdge(Vertice(12), Vertice(6)),
        UndirectedEdge(Vertice(7), Vertice(1)), UndirectedEdge(Vertice(1), Vertice(9)), UndirectedEdge(Vertice(1), Vertice(13)),
        UndirectedEdge(Vertice(1), Vertice(8)), UndirectedEdge(Vertice(1), Vertice(18)), UndirectedEdge(Vertice(11), Vertice(13)))
    )

    RootedTree.createFromUnRootedTree(unRootedTree2, Vertice(17), 0) should be (RootedTree(
      Node.withChildren(17, Vertice(-1), List(
        Node(2, Vertice(17)),
        Node(19, Vertice(17)),
        Node.withChildren(4, Vertice(17), List(
          Node(3, Vertice(4)),
          Node.withChildren(6, Vertice(4), List(
            Node(15, Vertice(6)),
            Node.withChildren(7, Vertice(6), List(
              Node.withChildren(1, Vertice(7), List(
                Node(9, Vertice(1)),
                Node.withChildren(13, Vertice(1), List(
                  Node(11, Vertice(13))
                )),
                Node(8, Vertice(1)),
                Node(18, Vertice(1))
              ))
            )),
            Node(12, Vertice(6))
          ))
        )),
        Node.withChildren(10, Vertice(17), List(
          Node(16, Vertice(10)),
          Node(14, Vertice(10)),
          Node(5, Vertice(10))
        ))
      ))))

    val unRootedTree3 = UnRootedTree(
      List(Vertice(1),Vertice(2),Vertice(3),Vertice(4),Vertice(5),Vertice(6),Vertice(7),Vertice(8),Vertice(9),Vertice(10),
        Vertice(11),Vertice(12),Vertice(13),Vertice(14),Vertice(15),Vertice(16),Vertice(17),Vertice(18),Vertice(19)),
      List(UndirectedEdge(Vertice(17), Vertice(2)),UndirectedEdge(Vertice(17), Vertice(19)), UndirectedEdge(Vertice(4), Vertice(17)),
        UndirectedEdge(Vertice(10), Vertice(17)), UndirectedEdge(Vertice(10), Vertice(16)), UndirectedEdge(Vertice(10), Vertice(14)),
        UndirectedEdge(Vertice(10), Vertice(5)), UndirectedEdge(Vertice(4), Vertice(3)), UndirectedEdge(Vertice(4), Vertice(6)),
        UndirectedEdge(Vertice(6), Vertice(15)), UndirectedEdge(Vertice(6), Vertice(7)), UndirectedEdge(Vertice(12), Vertice(6)),
        UndirectedEdge(Vertice(7), Vertice(1)), UndirectedEdge(Vertice(1), Vertice(9)), UndirectedEdge(Vertice(1), Vertice(13)),
        UndirectedEdge(Vertice(1), Vertice(8)), UndirectedEdge(Vertice(1), Vertice(18)), UndirectedEdge(Vertice(11), Vertice(13)))
    )

    RootedTree.createFromUnRootedTree(unRootedTree3, Vertice(17), 0) should be (RootedTree(
      Node.withChildren(17, Vertice(-1), List(
        Node(2, Vertice(17)),
        Node(19, Vertice(17)),
        Node.withChildren(4, Vertice(17), List(
          Node(3, Vertice(4)),
          Node.withChildren(6, Vertice(4), List(
            Node(15, Vertice(6)),
            Node.withChildren(7, Vertice(6), List(
              Node.withChildren(1, Vertice(7), List(
                Node(9, Vertice(1)),
                Node.withChildren(13, Vertice(1), List(
                  Node(11, Vertice(13))
                )),
                Node(8, Vertice(1)),
                Node(18, Vertice(1))
              ))
            )),
            Node(12, Vertice(6))
          ))
        )),
        Node.withChildren(10, Vertice(17), List(
          Node(16, Vertice(10)),
          Node(14, Vertice(10)),
          Node(5, Vertice(10))
        ))
      ))))
  }

  "Function height" should "return " in {
    RootedTree(
      Node.withChildren(5, Vertice(-1), List(
        Node(1, Vertice(5)),
        Node(4, Vertice(5)),
        Node.withChildren(6, Vertice(5), List(
          Node.withChildren(2, Vertice(6), List(
            Node(3, Vertice(2))
          )),
          Node(7, Vertice(6)),
          Node(8, Vertice(6)),
          Node.withChildren(9, Vertice(6), List(
            Node(12, Vertice(9)),
            Node(13, Vertice(9)),
            Node(14, Vertice(9))
          )),
          Node.withChildren(10, Vertice(6), List(
            Node(11, Vertice(10))
          )))),
        Node.withChildren(15, Vertice(5), List(
          Node(16, Vertice(15)),
          Node.withChildren(17, Vertice(15), List(
            Node(18, Vertice(17)),
            Node(19, Vertice(17))))))
      ))).height() should be (4)
  }
}
