package pl.elka.gis18z.algorithm

trait AbstractVertice{
  def id: Int
}
case class Vertice(override val id: Int) extends AbstractVertice
