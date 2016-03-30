@ECHO OFF

echo Running Gradle...
call gradle clean build jacocoTestReport javadocs

echo Running Acceptance Tests...
cd acceptance
call javac -cp .;..\build\classes\main ArgsparserTestsKeywords.java
call java -cp .;..\build\classes\main;C:\RobotFramework\robotframework-2.9.jar org.robotframework.RobotFramework ArgsparserTests.txt

set /P INPUT= Would you like to see the reports? (Y/N): 
if /I "%INPUT%"=="n" goto no

cd ..
start "" "file://%CD%\build\reports\tests\index.html"
start "" "file://%CD%\build\reports\jacoco\test\html\index.html"
start "" "file://%CD%\acceptance\report.html"

:no
exit 0