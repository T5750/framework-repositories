@echo off

REM ------------------------------------------------------------------------
REM https://github.com/T5750/framework-repositories/blob/master/dubbo/doc/DubboNote.md#3-dubbo-ops
REM mvn clean package
REM mvn --projects dubbo-admin-server spring-boot:run
REM ------------------------------------------------------------------------
echo.
echo.
echo             Choose the command
echo 1-----------------run-------------------
echo 2-------------clean and run-------------
set /p num=Choose:
if"%num%"=="1" (
mvn --projects dubbo-admin-server spring-boot:run
)
if"%num%"=="2" (
mvn clean package
mvn --projects dubbo-admin-server spring-boot:run
)
REM :exit