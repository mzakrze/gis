package pl.elka.gis18z.algorithm

import org.scalatest.{FlatSpec, Matchers}
import pl.elka.gis18z.algorithm.rootedisomorphic.{Node, RootedIsomorphic, RootedTree}

class RootedIsomorphicTest extends FlatSpec with Matchers {

  "Trees" should "be isomorphic" in {

    val tree1 = RootedTree (
      Node.withChildren(12, Vertice(-1), List(
        Node(2, Vertice(12), treeId=1),
        Node.withChildren(3, Vertice(12), List(
          Node.withChildren(5, Vertice(3), List(
            Node.withChildren(8, Vertice(5), List(
              Node(9, Vertice(8), treeId=1)
            ), 1)
          ), 1)), 1),
        Node.withChildren(11, Vertice(12), List(
          Node.withChildren(13, Vertice(11), List(
            Node.withChildren(1, Vertice(13), List(
              Node.withChildren(6, Vertice(1), List(
                Node.withChildren(7, Vertice(6), List(
                  Node.withChildren(4, Vertice(7), List(
                    Node(10, Vertice(4), treeId=1)
                  ), 1)
                ), 1)
              ), 1)
            ), 1)
          ), 1)), 1)
      ), 1))

    val tree2 = RootedTree (
      Node.withChildren(12, Vertice(-1), List(
        Node(2, Vertice(12)),
        Node.withChildren(3, Vertice(12), List(
          Node.withChildren(5, Vertice(3), List(
            Node.withChildren(8, Vertice(5), List(
              Node(9, Vertice(8))
            ))
          )))),
        Node.withChildren(11, Vertice(12), List(
          Node.withChildren(13, Vertice(11), List(
            Node.withChildren(1, Vertice(13), List(
              Node.withChildren(6, Vertice(1), List(
                Node.withChildren(7, Vertice(6), List(
                  Node.withChildren(4, Vertice(7), List(
                    Node(10, Vertice(4))
                  ))
                ))
              ))
            ))
          ))))
      )))

    RootedIsomorphic.rootedIsomorphic(tree1, tree2).map(t => (t._1.id, t._2.id)).sortBy(_._1)should be (
      List((1,1), (2,2), (3,3), (4,4), (5,5), (6,6),(7,7),
      (8,8), (9,9), (10,10), (11,11), (12,12), (13,13)))

    val tree11 = RootedTree(
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
      )))

    val tree12 = RootedTree (
      Node.withChildren(6, Vertice(-1), List(
        Node(5, Vertice(6)),
        Node(19, Vertice(6)),
        Node.withChildren(7, Vertice(6), List(
          Node.withChildren(1, Vertice(7), List(
            Node(2, Vertice(1)),
            Node.withChildren(13, Vertice(1), List(
              Node(11, Vertice(13))
            ))
          )),
          Node(3, Vertice(7)))),
        Node.withChildren(4, Vertice(6), List(
          Node.withChildren(17, Vertice(4), List(
            Node(12, Vertice(17)),
            Node(15, Vertice(17)),
            Node(9, Vertice(17)),
            Node(16, Vertice(17)),
            Node(18, Vertice(17)),
            Node.withChildren(10, Vertice(17), List(
              Node(14, Vertice(10))
            ))))))
      )))

    RootedIsomorphic.rootedIsomorphic(tree11, tree12).map(t => (t._1.id, t._2.id)).sortBy(_._1)should be (List.empty)
  }

  "Trees" should "not be isomorphic" in {
    val tree1 = RootedTree (
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
      )))

    val tree2 = RootedTree (
      Node.withChildren(115, Vertice(-1), List(
        Node(111, Vertice(115)),
        Node(114, Vertice(115)),
        Node.withChildren(116, Vertice(115), List(
          Node.withChildren(112, Vertice(116), List(
            Node(113, Vertice(112))
          )),
          Node(117, Vertice(116)),
          Node(118, Vertice(116)),
          Node.withChildren(119, Vertice(116), List(
            Node(1112, Vertice(119)),
            Node(1113, Vertice(119)),
            Node(1114, Vertice(119))
          )),
          Node.withChildren(1110, Vertice(116), List(
            Node(1111, Vertice(1110))
          ))))
      )))

    RootedIsomorphic.rootedIsomorphic(tree1, tree2).map(t => (t._1.id, t._2.id)).sortBy(_._1)should be (List.empty)
  }

}
