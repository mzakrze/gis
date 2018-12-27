package pl.elka.gis18z.algorithm.rootedisomorphic

import pl.elka.gis18z.algorithm._

sealed trait Tree

object Node {
  def withChildren(id: Int, parent: AbstractVertice, children: Seq[Node] = Seq.empty, treeId: Int = 0): Node = {
    new Node(id = id, treeId = treeId, parent = parent, children = children)
  }
  def apply(id: Int, parent: AbstractVertice, treeId: Int ): Node = {
    new Node(id = id, treeId = treeId, parent = parent)
  }
  def apply(id: Int, parent: AbstractVertice): Node = {
    new Node(id = id, treeId = 0, parent = parent)
  }
}

case class Node(id: Int,
                treeId: Int,
                parent: AbstractVertice,
                var label: Option[Int] = None,
                var orderedLabel: Seq[Int] = Seq.empty,
                var orderedChildren: Seq[Node] = Seq.empty,
                var children: Iterable[Node] = Iterable.empty) extends Tree {

  def findNode(id: Int): Option[Node] = this match {
    case n: Node if n.id == id => Some(n)
    case n: Node if n.children.isEmpty => None
    case n: Node => n.children.flatMap(m => m.findNode(id)).headOption
  }

  def mapNode(id: Int, node: Node): Unit = this match {
    case n: Node if n.id == id =>
      this.label = node.label
      this.orderedLabel = node.orderedLabel
      this.orderedChildren = node.orderedChildren
    case n: Node => n.children.foreach(m => m.mapNode(id, node))
  }
}

object RootedTree {
  def createFromUnRootedTree(unRootedTree: UnRootedTree,
                             centerVertice: AbstractVertice,
                             id: Int): RootedTree = {
    RootedTree(createTree(Vertice(-1), centerVertice, unRootedTree, id))
  }

  private def createTree(parent: AbstractVertice,
                         centerVertice: AbstractVertice,
                         unRootedTree: UnRootedTree,
                         id: Int): Node = {
    val groupedEdges = unRootedTree.edges.groupBy(v => v.v1.id == centerVertice.id || v.v2.id == centerVertice.id)
    groupedEdges.getOrElse(true, Nil) match {
      case Nil => Node(centerVertice.id, parent, id)
      case e: List[UndirectedEdge] =>
          Node(
            centerVertice.id,
            id,
            parent,
            None, Seq.empty, Seq.empty,
            e.map(edge => {
              val vertice = if (edge.v1.id == centerVertice.id) edge.v2 else edge.v1
              createTree(
                centerVertice,
                vertice,
                unRootedTree.copy(edges = groupedEdges.getOrElse(false, Nil), vertices = unRootedTree.vertices.filter(_ != centerVertice)),
                id)
            }))
    }
  }
}

case class RootedTree(root: Tree){
  def height(tree: Tree = root): Int = tree match {
    case n: Node if n.children.isEmpty => 0
    case n: Node => 1 + n.children.map(t => height(t)).max
  }

  def dfs(fun: (Int, Node) => Unit, tree: Tree = root, depth: Int = 0): Unit = tree match {
    case n: Node if n.children.isEmpty => fun(depth, n)
    case n: Node =>
      n.children.foreach(t => dfs(fun, t, depth+1))
      fun(depth, n)
  }

  def findNode(id: Int): Option[Node] = root match {
    case n: Node => n.findNode(id)
  }

  def mapNode(node: Node): Unit = root match {
    case n: Node => n.mapNode(node.id, node)
  }
}
