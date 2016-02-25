@ECHO OFF
cd "demo program"
title Volume Calculator Demo
@ECHO ON
javac -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator.java
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7 5 2 
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7 5 2 4 5 6
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator -h
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7.5 2.4 --type sphere 8.2
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7.5 2.4 --type cylinder 8.2 --digits 3
@ECHO OFF
pause