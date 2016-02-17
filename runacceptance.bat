cd acceptance
javac -cp .;..\build\classes\main ArgsparserTestsKeywords.java
java -cp .;..\build\classes\main;C:\RobotFramework\robotframework-2.9.jar org.robotframework.RobotFramework ArgsparserTests.txt
cd ..
pause