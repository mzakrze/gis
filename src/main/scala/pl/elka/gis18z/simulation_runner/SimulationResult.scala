package pl.elka.gis18z.simulation_runner

case class ProblemSize(n: Int, depth: Int) {}

case class ProblemSolutionResult(time: Long, areIsomorphic: Boolean)

case class SimulationResult(problemSizeToSolution: Map[ProblemSize, ProblemSolutionResult])