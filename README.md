# Cucumber Java Selenium Automation Framework

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Maven](https://img.shields.io/badge/Maven-Build-orange.svg)
![Cucumber](https://img.shields.io/badge/Cucumber-7.34.3-brightgreen.svg)
![JUnit](https://img.shields.io/badge/JUnit-4.13.2-red.svg)
![Selenium](https://img.shields.io/badge/Selenium-4.45.0-green.svg)
![IntelliJ IDEA](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-black.svg)

---

## Overview

This project is an automation testing framework built using **Cucumber Java**, **JUnit**, **Selenium WebDriver**, and **Maven**.

The framework is used to automate web application testing with BDD scenarios written in **Gherkin syntax**.

The current automation test validates the product search feature on the Bibit web application.

---

## Tech Stack

* Java JDK 21
* Maven
* Cucumber Java
* JUnit 4
* Selenium WebDriver
* IntelliJ IDEA

---


## How to Run Test

Open the runner file:

```text
src/test/java/runner/TestRunner.java
```

Run the test from IntelliJ IDEA by clicking the **Run** button on `TestRunner.java`.

To run a specific scenario, update the tag value inside `TestRunner.java`.


## Scope Adjustment

The original assigned feature could not be automated as expected because the login flow required OTP verification through Email and WhatsApp, followed by PIN input.

Due to this constraint, the automation scenario was adjusted to test the product search feature without login.

## Notes

Before running the test, make sure:

* Java JDK 21 is installed
* Maven is installed
* IntelliJ IDEA is installed
* Target website is accessible
* Browser can be opened by Selenium WebDriver
* Microsoft Edge

---

