cd acceptance
javac -cp .;..\build\classes\main ArgsparserTestsFt1Keywords.java
java -cp .;..\build\classes\main;C:\RobotFramework\robotframework-2.9.jar org.robotframework.RobotFramework ArgsparserTestsFt1.txt
cd ..