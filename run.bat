set ProjectPathTests=src
set ProjectPathTestsTests=src\tests
set SeleniumPath=libs\selenium-java-3.4.0.jar
set SeleniumPathOpenqa=libs\client-combined-3.4.0-nodeps.jar
set JcommanderPath=libs\jcommander-1.27.jar
set WebdriverPath=libs\*
set TestNGPath=libs\testng-6.11.jar
set classpath=%TestNGPath%;%SeleniumPath%;%SeleniumPathOpenqa%;%WebdriverPath%;%JcommanderPath%;%SeleniumLibPath%;%SeleniumLibPathE%;%ProjectPathTests%;%ProjectPathTestsTests%
javac src\tests\*
java org.testng.TestNG testng.xml
pause