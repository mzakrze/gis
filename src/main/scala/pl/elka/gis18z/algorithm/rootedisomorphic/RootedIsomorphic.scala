package pl.elka.gis18z.algorithm.rootedisomorphic

import pl.elka.gis18z.algorithm.Vertice

object RootedIsomorphic {
  def rootedIsomorphic(tree1: RootedTree, tree2: RootedTree): List[(Vertice, Vertice)] = {
    val h = Math.max(tree1.height(), tree2.height())
    val L = collection.mutable.Map[Int, List[(Int, Int)]]()
    val tree1Id = tree1.root.asInstanceOf[Node].treeId
    val tree2Id = tree2.root.asInstanceOf[Node].treeId

    tree1.dfs((depth, vertice) => L(depth) = (vertice.treeId, vertice.id) :: L.getOrElse(depth, List.empty))
    tree2.dfs((depth, vertice) => L(depth) = (vertice.treeId, vertice.id) :: L.getOrElse(depth, List.empty))


    (0 until h).reverse foreach(i => {
      L(i+1).indices foreach(j => {
        val v = L(i+1)(j)

        v._1 match {
          case `tree1Id` => setOrderedLabelAndChildren(tree1, v._2)
          case `tree2Id` => setOrderedLabelAndChildren(tree2, v._2)
        }
      })

      val LiNodes: List[Node] = L(i).flatMap(l => l._1 match {
        case `tree1Id` => tree1.findNode(l._2)
        case `tree2Id` => tree2.findNode(l._2)
      })

      L(i) = LiNodes.sortWith(sortSet).map(n => (n.treeId, n.id))
      val ranking = LiNodes.groupBy(_.orderedLabel).keys.toList.reverse
      LiNodes.foreach {
        case n if n.treeId == `tree1Id` => tree1.mapNode(n.copy(label = Some(ranking.indexOf(n.orderedLabel))))
        case n if n.treeId == `tree2Id` => tree2.mapNode(n.copy(label = Some(ranking.indexOf(n.orderedLabel))))
      }
    })

    val root1 = tree1.root.asInstanceOf[Node]
    val root2 = tree2.root.asInstanceOf[Node]

    if(root1.label == root2.label) {
      val mapping = generateMapping(root1, root2,List.empty).map(n => (Vertice(n._1.id),Vertice(n._2.id)))
      if(mapping.length == L.map(_._2.length).sum/2) mapping else List.empty
    } else
      List.empty
  }

  private def setOrderedLabelAndChildren(tree: RootedTree, verticeId: Int) = {
    val vNode = tree.findNode(verticeId).get
    val vParent = tree.findNode(vNode.parent.id).get

    if(vNode.label.isDefined) {
      vParent.orderedLabel = vParent.orderedLabel :+ vNode.label.get
    } else {
      vParent.orderedLabel = vParent.orderedLabel :+ 0
    }
    vParent.orderedChildren = vParent.orderedChildren :+ vNode


    tree.mapNode(vParent)
  }

  private def sortSet(v: Node, w: Node): Boolean = {
    sortedLeg(v.orderedLabel.toList, w.orderedLabel.toList)
  }

  private def sortedLeg(a: Seq[Int], b: Seq[Int]): Boolean = (a,b) match {
    case (Nil, Nil) => true
    case (_, Nil) => true
    case (Nil, _) => false
    case (x :: rest1, y :: rest2) if x == y => sortedLeg(rest1,rest2)
    case (x :: _, y :: _) => x < y
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
