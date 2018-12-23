package pl.elka.gis18z.algorithm.rootedisomorphic

import pl.elka.gis18z.algorithm.Vertice

object RootedIsomorphic {
  def rootedIsomorphic(tree1: RootedTree, tree2: RootedTree): List[(Vertice, Vertice)] = {
    val h = Math.max(tree1.height(), tree2.height())
    val L = collection.mutable.Map[Int, List[Node]]()

    tree1.dfs((depth, vertice) => L(depth) = vertice :: L.getOrElse(depth, List.empty))
    tree2.dfs((depth, vertice) => L(depth) = vertice :: L.getOrElse(depth, List.empty))

    (0 until h).reverse foreach(i => {
      L(i+1).indices foreach(j => {
        val v = L(i+1)(j)
        val vParent = L(i).find(n => n.id == v.parent.id && n.treeId == v.treeId).get
        val newVParent = vParent.copy(orderedLabel = vParent.orderedLabel :+ v.label,
                                      orderedChildren = vParent.orderedChildren :+ v)
        L(i) = L(i).map(n => if(n.id == newVParent.id && n.treeId == v.treeId) newVParent else n)
      })

      L(i) = L(i).sortWith(sortSet)
      val ranking = L(i).groupBy(_.orderedLabel.length).keys.toList
      L(i) = L(i).map(n => n.copy(label = ranking.indexOf(n.orderedLabel.length)))
    })

    if(L.last._2.head.label == L.last._2.last.label) {
      val mapping = generateMapping(L.last._2.head, L.last._2.last,List.empty).map(n => (Vertice(n._1.id),Vertice(n._2.id)))
      if(mapping.length == L.map(_._2.length).sum/2) mapping else List.empty
    } else
      List.empty
  }

  private def sortSet(v: Node, w: Node): Boolean = {
    sortedLeg(v.orderedLabel.toList, w.orderedLabel.toList)
  }

  private def sortedLeg(a: List[Int], b: List[Int]): Boolean = (a,b) match {
    case (Nil, Nil) => true
    case (_, Nil) => true
    case (Nil, _) => false
    case (x :: rest1, y :: rest2) if x == y => sortedLeg(rest1,rest2)
    case (x :: _, y :: _) => x > y
  }

  private def generateMapping(v: Node, w: Node, map: List[(Node, Node)]): List[(Node, Node)] = {
    val nMap = v.orderedChildren.zip(w.orderedChildren) match {
      case Nil => map
      case children => children flatMap {
        case (a, b) => generateMapping(a, b, map)
      }
    }
    (v,w) :: nMap.toList
  }
}
