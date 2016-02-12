cd "demo program"
javac -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator.java
cls
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7 5 2 
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7 5 2 4 5 6
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator -h
pause