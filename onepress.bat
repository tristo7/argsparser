@ECHO OFF
title Running: Gradle
call gradle clean build jacocoTestReport
title Running: Acceptance
call runacceptance.bat
start "" "file://%CD%\build\reports\tests\index.html"
start "" "file://%CD%\build\reports\jacoco\test\html\index.html"
start "" "file://%CD%\acceptance\report.html"