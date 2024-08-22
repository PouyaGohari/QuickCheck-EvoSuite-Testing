# QuickCheck-EvoSuite-Testing
This repository contains the solutions to the third computer assignment for the Software Testing course at the University of Tehran. The assignment involves cloning an existing project, applying QuickCheck with a given specification, and using EvoSuite to generate tests and modify the test oracle.

## Table of Contents

- [Introduction](#introduction)
- [Assignment Overview](#assignment-overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Results and Analysis](#results-and-analysis)
- [Report](#report)
- [License](#license)

## Introduction

This project focuses on applying advanced software testing techniques, specifically QuickCheck for property-based testing and EvoSuite for automated test generation. These methods are applied to a cloned project to evaluate and enhance its testing suite.

## Assignment Overview

### Part 1: Applying QuickCheck

- **Task:** Clone the [ST03_HW2 project](https://github.com/Szpilman2/ST03_HW2) and apply QuickCheck based on the provided specification.
- **Objective:** Use QuickCheck to automatically generate test cases and verify the correctness of the code against the specified properties.

### Part 2: Applying EvoSuite

- **Task:** Use EvoSuite to generate test cases for the cloned project and modify the test oracle to better suit the project’s requirements.
- **Objective:** Automatically generate a comprehensive set of test cases with EvoSuite and refine the test oracle to ensure accurate test outcomes.

## Prerequisites

Before running the tests, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Gradle
- QuickCheck library
- EvoSuite tool
- An IDE with JUnit support (e.g., IntelliJ IDEA, Eclipse)

## Installation

To set up the project locally, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/PouyaGohari/QuickCheck-EvoSuite-Testing.git
    cd QuickCheck-EvoSuite-Testing
    ```

2. Ensure that Gradle, QuickCheck, and EvoSuite are installed and set up on your system.

3. Open the project in your IDE and allow it to download the necessary dependencies.

## Usage

### Running QuickCheck Tests

1. Navigate to the project directory.
2. Run the QuickCheck tests using Gradle:
    ```sh
    ./gradlew test
    ```
3. Review the test results in the console output or test runner tab of your IDE.

### Running EvoSuite Tests

1. Use EvoSuite to generate tests for the project:
    ```sh
    java -jar evosuite.jar -generateTests -projectCP target/classes -Dsearch_budget=60
    ```
2. Modify the generated test oracle as needed to align with the project’s requirements.
3. Run the EvoSuite-generated tests to verify their effectiveness:
    ```sh
    ./gradlew test
    ```

## Results and Analysis

- **QuickCheck Testing:** Successfully applied QuickCheck to automatically generate test cases based on the provided specification, ensuring the code adheres to specified properties.
- **EvoSuite Testing:** Generated a comprehensive set of test cases using EvoSuite, then refined the test oracle to improve the accuracy and reliability of the tests.

## Report

A detailed report documenting the research, methodology, implementation, and analysis for each part of the assignment is available [here](HW3_REPORT.pdf).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
