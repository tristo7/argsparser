@ECHO OFF
title Running: Gradle
call gradle clean build jacocoTestReport
start "" "file://%CD%\build\reports\tests\index.html"
start "" "file://%CD%\build\reports\jacoco\test\html\index.html"
title Running: Acceptance
call runacceptance.bat
start "" "file://%CD%\acceptance\report.html"
pause