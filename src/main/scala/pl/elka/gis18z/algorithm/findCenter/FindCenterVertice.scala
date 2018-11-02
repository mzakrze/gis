package pl.elka.gis18z.algorithm.findCenter

import pl.elka.gis18z.algorithm.AbstractVertice

case class FindCenterVertice(override val id: Int, var centerDegree: Int) extends AbstractVertice