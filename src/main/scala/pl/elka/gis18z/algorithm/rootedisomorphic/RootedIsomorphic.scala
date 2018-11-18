package pl.elka.gis18z.algorithm.rootedisomorphic

import pl.elka.gis18z.algorithm.{Node, RootedTree}

//TODO make it better
object RootedIsomorphic {
  def rootedIsomorphic(tree1: RootedTree, tree2: RootedTree): Seq[(Int, Int)] = {
    val h = Math.max(tree1.height(), tree2.height())
    val L = collection.mutable.Map[Int, List[Node]]()

    tree1.bfs((depth, vertice) => L(depth) = vertice :: L.getOrElse(depth, List.empty))
    tree2.bfs((depth, vertice) => L(depth) = vertice :: L.getOrElse(depth, List.empty))

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
      L(i) = L(i).map(n => {
        n.copy(label = ranking.indexOf(n.orderedLabel.length))
      })
    })
    if(L.last._2(0).label == L.last._2(1).label) {
      generateMapping(L.last._2(0), L.last._2(1),collection.mutable.Seq.empty).map(n => (n._1.id, n._2.id)).groupBy( v => (v._1,v._2)).keys.toList
    } else
      Seq.empty
  }

  def generateMapping(v: Node, w: Node, map: collection.mutable.Seq[(Node, Node)]): Seq[(Node, Node)] = {
    if(v.orderedChildren.isEmpty)
      map :+ (v, w)
    else
    v.orderedChildren.zip(w.orderedChildren) flatMap  {
      case(a,b) => map ++ generateMapping(a, b, map :+ (v, w))
    }
  }
}
