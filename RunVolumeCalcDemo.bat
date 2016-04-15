@ECHO OFF
cd "demo program"
title Volume Calculator Demo
@ECHO ON
javac -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator.java
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator -h
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7.3 5.78 2.22 -t box
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 52.678 -t sphere 0 0
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7.345 2.224 0 -t cylinder -d 3
@ECHO OFF
pause