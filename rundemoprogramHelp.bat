cd "demo program"
javac -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator.java
java -cp .;..\build\libs\argsparser-1.0.jar VolumeCalculator -h
timeout /t 30
cd ..