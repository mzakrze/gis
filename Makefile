build:
	sbt assembly

# TODO - obtain name and version
run_example:
	java -Xms512m -Xmx4g -jar target/scala-2.12/gis-assembly-0.1.jar --n1 100 --n2 200 --ns 100 --d1 10 --d2 20 --ds 2 --repeat 5 --output result

run_eta_12h:
	java -Xms6g -Xmx6g -jar target/scala-2.12/gis-assembly-0.1.jar --n1 1000 --n2 3000 --ns 200 --d1 100 --d2 300 --ds 15 --repeat 5 --output result_eta_12h

run_eta_14h:
	java -Xms6g -Xmx6g -jar target/scala-2.12/gis-assembly-0.1.jar --n1 1000 --n2 3000 --ns 200 --d1 300 --d2 400 --ds 5 --repeat 5 --output result_eta_14h

run_eta_8h:
	java -Xms6g -Xmx6g -jar target/scala-2.12/gis-assembly-0.1.jar --n1 1000 --n2 3000 --ns 200 --d1 400 --d2 500 --ds 5 --repeat 5 --output result_eta_8h
