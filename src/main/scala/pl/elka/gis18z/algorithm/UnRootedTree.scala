package pl.elka.gis18z.algorithm

case class UnRootedTree(vertices: List[AbstractVertice], edges: List[UndirectedEdge]) {
  def verticesDegree(id: Int) = {
    edges.count(e => e.v1.id == id || e.v2.id == id)
  }

  def getNeighbours(id: Int) = {
    edges.filter(e => e.v1.id == id || e.v2.id == id).map {
      case e: UndirectedEdge if e.v1.id == id => e.v2
      case e: UndirectedEdge if e.v2.id == id => e.v1
    }.toSet
  }
}
