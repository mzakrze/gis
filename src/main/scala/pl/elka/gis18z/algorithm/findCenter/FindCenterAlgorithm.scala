package pl.elka.gis18z.algorithm.findCenter

import pl.elka.gis18z.algorithm.UnRootedTree

object FindCenterAlgorithm {
  def findCenter(unRootedTree: UnRootedTree): Set[FindCenterVertice] = {
    val mappedTree = unRootedTree.vertices.map(v => v.id -> FindCenterVertice(v.id, unRootedTree.verticesDegree(v.id))).toMap
    val filteredTree = mappedTree.filter(_._2.centerDegree <= 1)

    def getCenter(tree: Map[Int, FindCenterVertice], r: Int): Map[Int, FindCenterVertice] = {
      if(r <= 0) tree
      else {
        val neighbors = tree.flatMap(v => {
          unRootedTree.getNeighbours(v._1).map(n => {
            val neighbour = mappedTree(n.id)
            neighbour.centerDegree = neighbour.centerDegree - 1
            neighbour
          }).filter(_.centerDegree == 1).map(n => n.id -> n)
        })

        getCenter(neighbors, r - neighbors.size)
      }
    }

    getCenter(filteredTree, unRootedTree.vertices.length - filteredTree.size).values.toSet
  }
}
