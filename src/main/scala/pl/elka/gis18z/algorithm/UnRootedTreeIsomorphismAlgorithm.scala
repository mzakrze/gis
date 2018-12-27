package pl.elka.gis18z.algorithm

import pl.elka.gis18z.algorithm.findCenter.FindCenterAlgorithm
import pl.elka.gis18z.algorithm.rootedisomorphic.{RootedIsomorphic, RootedTree}

object UnRootedTreeIsomorphismAlgorithm {
  def areIsomorphic(tree1: UnRootedTree, tree2: UnRootedTree): Solution = {
    val center1 = FindCenterAlgorithm.findCenter(tree1)
    var rootedTree1 = RootedTree.createFromUnRootedTree(tree1, center1.head, 1)
    val center2 = FindCenterAlgorithm.findCenter(tree2)
    var rootedTree2 = RootedTree.createFromUnRootedTree(tree2, center2.head, 2)

    var mapping = RootedIsomorphic.rootedIsomorphic(rootedTree1, rootedTree2)
    mapping match {
      case Nil if center2.size > 1 =>
        rootedTree1 = RootedTree.createFromUnRootedTree(tree1, center1.head, 1)
        rootedTree2 = RootedTree.createFromUnRootedTree(tree2, center2.last, 2)
        mapping = RootedIsomorphic.rootedIsomorphic(rootedTree1, rootedTree2)
        Solution(mapping.nonEmpty, mapping)
      case Nil => Solution(false, List.empty)
      case _ => Solution(true, mapping)
    }
  }
}
