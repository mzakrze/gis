package pl.elka.gis18z.algorithm

sealed trait Tree extends AbstractVertice

object Node {
  def withChildren(id: Int, parent: AbstractVertice, children: Seq[Node] = Seq.empty): Node = {
    new Node(id = id, parent = parent, children = children)
  }
  def apply(id: Int, parent: AbstractVertice): Node = {
    new Node(id = id, parent = parent)
  }
}
case class Node(id: Int,
                parent: AbstractVertice,
                label: Int = 0,
                orderedLabel: Seq[Int] = Seq.empty,
                orderedChildren: Seq[Node] = Seq.empty,
                children: Iterable[Node] = Iterable.empty) extends Tree

object RootedTree {
  def createFromUnRootedTree(unRootedTree: UnRootedTree,
                             centerVertice: AbstractVertice): RootedTree = {
    RootedTree(createTree(Vertice(-1), centerVertice, unRootedTree).head)
  }

  private def createTree(parent: AbstractVertice,
                         centerVertice: AbstractVertice,
                         unRootedTree: UnRootedTree): Seq[Node] = {
    unRootedTree.edges.filter(v => v.v1 == centerVertice || v.v2 == centerVertice) match {
      case Nil => Seq(Node(centerVertice.id, parent))
      case e: List[UndirectedEdge] =>
        Seq(
          Node(
            centerVertice.id,
            parent,
            0, Seq.empty, Seq.empty,
            e.flatMap(edge => {
              val vertice = if (edge.v1 == centerVertice) edge.v2 else edge.v1
              createTree(
                centerVertice,
                vertice,
                unRootedTree.copy(edges = unRootedTree.edges.filter(v => v.v1 != centerVertice && v.v2 != centerVertice),
                  vertices = unRootedTree.vertices.filter(_ != centerVertice).map(_.asInstanceOf[Node])))
            })))
    }
  }
}

case class RootedTree(root: Tree){
  def height(tree: Tree = root): Int = tree match {
    case n: Node if n.children.isEmpty => 0
    case n: Node => 1 + n.children.map(t => height(t)).max
  }

  def bfs(fun: (Int, Node) => Unit, tree: Tree = root, depth: Int = 0): Unit = tree match {
    case n: Node if n.children.isEmpty => fun(depth, n)
    case n: Node =>
      n.children.foreach(t => bfs(fun, t, depth+1))
      fun(depth, n)
  }
}
