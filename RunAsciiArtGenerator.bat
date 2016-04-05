cd "demo program"
title ASCII Art Generator Demo
javac -cp .;..\build\libs\argsparser-1.0.jar AsciiArtGenerator.java
java -cp .;..\build\libs\argsparser-1.0.jar AsciiArtGenerator triangle 5 -p x
java -cp .;..\build\libs\argsparser-1.0.jar AsciiArtGenerator square 5 -p @
java -cp .;..\build\libs\argsparser-1.0.jar AsciiArtGenerator diamond 7
java -cp .;..\build\libs\argsparser-1.0.jar AsciiArtGenerator squiggle 7
pause