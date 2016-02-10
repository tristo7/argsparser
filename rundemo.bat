cd "demo program"
javac -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator.java
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 1 2 3 
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 1 2 3 4
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator -h
pause