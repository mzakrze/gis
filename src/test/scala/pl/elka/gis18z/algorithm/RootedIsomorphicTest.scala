package pl.elka.gis18z.algorithm

import org.scalatest.{FlatSpec, Matchers}
import pl.elka.gis18z.algorithm.rootedisomorphic.{Node, RootedIsomorphic, RootedTree}

class RootedIsomorphicTest extends FlatSpec with Matchers {

  "An algorithm" should "work" in {

    val n1 = 10
    val n2 = 20
    val d1 = 3
    val d2 = 20
    val s = 10

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
          )))),
        Node.withChildren(1115, Vertice(115), List(
          Node(1116, Vertice(1115)),
          Node.withChildren(1117, Vertice(1115), List(
            Node(1118, Vertice(1117)),
            Node(1119, Vertice(1117))))))
      )))

    RootedIsomorphic.rootedIsomorphic(tree1, tree2).map(t => (t._1.id, t._2.id)).sortBy(_._1)should be (
      List((1,111), (2,112), (3,113), (4,114), (5,115), (6,116),(7,117),
      (8,118), (9,119), (10,1110), (11,1111), (12,1112), (13,1113), (14,1114), (15,1115), (16,1116), (17,1117), (18,1118), (19,1119)))
  }



}
