package pl.elka.gis18z.algorithm

trait AbstractVertice{
  def id: Int
}
case class Vertice(id: Int) extends AbstractVertice
