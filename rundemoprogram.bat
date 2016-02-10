cd "demo program"
javac -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator.java
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator 7 5 2
timeout /t 10
cd ..