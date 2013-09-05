@echo off

SET ANT_PATH=C:\Program Files\apache-ant-1.8.2

del /f bstorm.war
"%ANT_PATH%\bin\ant" -f build.xml all