@ECHO OFF
cd "demo program"
title Pizza Place Demo
@ECHO ON
javac -cp .;..\build\libs\argsparser-1.0.jar PizzaPlace.java
java -cp .;..\build\libs\argsparser-1.0.jar PizzaPlace -h
java -cp .;..\build\libs\argsparser-1.0.jar PizzaPlace small coke
java -cp .;..\build\libs\argsparser-1.0.jar PizzaPlace large water -cp -q 5

@ECHO OFF
pause