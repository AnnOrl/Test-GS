#!/bin/bash
javac -cp src:src/tests:libs/selenium-java-3.4.0.jar:libs/selenium-java-3.4.0/client-combined-3.4.0-nodeps.jar:libs/jcommander-1.27.jar:libs/selenium-java-3.4.0/lib/*:libs/testng-6.11.jar src/tests/Login.java
java -cp src:src/tests:libs/selenium-java-3.4.0.jar:libs/selenium-java-3.4.0/client-combined-3.4.0-nodeps.jar:libs/jcommander-1.27.jar:libs/selenium-java-3.4.0/lib/*:libs/testng-6.11.jar org.testng.TestNG testng.xml
