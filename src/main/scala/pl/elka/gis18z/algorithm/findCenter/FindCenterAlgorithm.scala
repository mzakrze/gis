package pl.elka.gis18z.algorithm.findCenter

import pl.elka.gis18z.algorithm.UnRootedTree

object FindCenterAlgorithm {
  def findCenter(unRootedTree: UnRootedTree): Set[FindCenterVertice] = {
    val mappedTree = collection.mutable.Map(unRootedTree.vertices.map(v => v.id -> FindCenterVertice(v.id, unRootedTree.verticesDegree(v.id))): _*)

    def getCenter(createdTree: Map[Int, FindCenterVertice], r: Int): Map[Int, FindCenterVertice] = {
      if(r <= 0) createdTree
      else {
        val neighbors = createdTree.flatMap(v => {
          unRootedTree.getNeighbours(v._1).map(n => {
            val neighbour = mappedTree(n.id)
            val newNeighbour = neighbour.copy(centerDegree = neighbour.centerDegree - 1)
            mappedTree(n.id) = newNeighbour
            newNeighbour
          }).filter(_.centerDegree == 1).map(n => n.id -> n)
        })

        getCenter(neighbors, r - neighbors.size)
      }
    }

    val filteredTree = unRootedTree.vertices.map(v => v.id -> FindCenterVertice(v.id, unRootedTree.verticesDegree(v.id))).toMap.filter(_._2.centerDegree <= 1)
    getCenter(filteredTree, unRootedTree.vertices.length - filteredTree.size).values.toSet
  }
}
