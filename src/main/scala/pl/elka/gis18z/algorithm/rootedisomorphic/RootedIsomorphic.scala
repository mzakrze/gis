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
        val vParent = L(i).find(_.id == v.parent.id).get
        val newVParent = vParent.copy(orderedLabel = vParent.orderedLabel :+ v.label,
                                      orderedChildren = vParent.orderedChildren :+ v)
        L(i) = L(i).map(n => if(n.id == newVParent.id) newVParent else n)
      })

      L(i) = L(i).sortBy(_.orderedLabel.length).reverse
      val ranking = L(i).groupBy(_.orderedLabel.length).keys.toList
      L(i) = L(i).map(n => n.copy(label = ranking.indexOf(n.orderedLabel.length)))
    })

    if(L.last._2.head.label == L.last._2.last.label) {
      generateMapping(L.last._2.head, L.last._2.last,List.empty).map(n => (Vertice(n._1.id),Vertice(n._2.id)))
    } else
      List.empty
  }

  def generateMapping(v: Node, w: Node, map: List[(Node, Node)]): List[(Node, Node)] = {
    val nMap = v.orderedChildren.zip(w.orderedChildren) match {
      case Nil => map
      case children => children flatMap {
        case (a, b) => generateMapping(a, b, map)
      }
    }
    (v,w) :: nMap.toList
  }
}
