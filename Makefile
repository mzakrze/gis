build:
	sbt assembly

# TODO - obtain name and version
run_example:
	java -jar target/scala-2.12/gis-assembly-0.1.jar --n1 100 --n2 200 --ns 100 --d1 10 --d2 20 --ds 2 --repeat 5 --output result

run_eta_96h:
	java -jar target/scala-2.12/gis-assembly-0.1.jar --n1 100 --n2 8000 --ns 200 --d1 100 --d2 300 --ds 15 --repeat 5 --output result_eta_96h
