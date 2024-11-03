Automation test for saucedemo 
=================

Java WebDriver test automation project for Saucedemo

How to run
====

Run from command line: 
> mvn -Dbrowser=chrome -Denvironment=dev clean test

Supported values for "browser" parameter:
* edge
* firefox
* chrome

"chrome" browser is default browser (even though it was not mentioned in the requirements)

Supported values for "environment" parameter: 
* dev
* qa
* staging 

THe property files contain the username/password to the site(in the real life these values should not be committed)