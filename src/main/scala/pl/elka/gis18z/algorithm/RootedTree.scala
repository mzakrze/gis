package pl.elka.gis18z.algorithm

sealed trait Tree extends AbstractVertice
case class Node(id: Int, parent: AbstractVertice, childrens: Seq[Tree] = Seq.empty) extends Tree

object RootedTree {
  def createFromUnRootedTree(unRootedTree: UnRootedTree, centerVertice: AbstractVertice): RootedTree = {
    RootedTree(createTree(Vertice(-1), centerVertice, unRootedTree).head)
  }

  private def createTree(parent: AbstractVertice, centerVertice: AbstractVertice, unRootedTree: UnRootedTree): Seq[Tree] = {
    unRootedTree.edges.filter(v => v.v1 == centerVertice || v.v2 == centerVertice) match {
      case Nil => Seq(Node(centerVertice.id, parent))
      case e: List[UndirectedEdge] =>
        Seq(Node(centerVertice.id, parent, e.flatMap(edge => {
          val vertice = if(edge.v1 == centerVertice) edge.v2 else edge.v1
          createTree(centerVertice,vertice,
            unRootedTree.copy(edges = unRootedTree.edges.filter(v => v.v1 != centerVertice && v.v2 != centerVertice),
              vertices = unRootedTree.vertices.filter(_ != centerVertice)))
        })))
    }
  }
}

case class RootedTree(root: Tree){
  def height(tree: Tree = root): Int = tree match {
    case n: Node if n.childrens.isEmpty => 1
    case n: Node => 1 + n.childrens.map(t => height(t)).max
  }
}
