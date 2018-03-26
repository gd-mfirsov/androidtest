<h1>About</h1>
This is a Test Framework for Shopping List.apk as a Certification test.

<H2>Technology stack</h2>

  - Java;
  
  - Appium;
  
  - Hamcrest;
  
  - TestNG;
  
  - Allure;
  
<h2>Prepare</h2>
First of all, to execute test suite you need to install Node.js and Appium server:

- Intall Node.js via brew:

  ```
  brew install node
  ```
- or Install Node.js from [link](https://nodejs.org/en/download/)

- Install Appium

  ```
  npm install -g appium
  ```

<h2>Execute</h2>

- Clone `androidtest` repository;

- Open folder `androidtest`

- Execute following command in Terminal:

  ```
  mvn clean install
  ```

<h2>Generate and open test report</h2>

- Install `Allure`

  ```
  npm install -g allure-commandline
  ```

- Navigate in Terminal to `androidtest` folder and execute command

  ```
  allure serve allure-results
  ```
