# Automated API Testing Framework for Pet Store API

## Overview
This project demonstrates a **BDD (Behavior-Driven Development)** automation framework for testing the Pet Store API. The framework is built with **Java** and utilizes modern testing tools to ensure comprehensive API validation. It is intended for **presentation purposes only**.

## Tools and Technologies
- **IDE**: IntelliJ IDEA
- **Build Tool**: Maven
- **Framework**: Cucumber BDD
- **Testing Frameworks**: JUnit, Rest Assured
- **Data-Driven Testing**: JSON files for test data storage

## Key Features

### BDD Framework Implementation
- Built using **Cucumber** with **Gherkin syntax** for human-readable test scenarios.
- Organized with folders:
    - `pojos`: For API models.
    - `runners`: For test execution.
    - `step_definitions`: For BDD steps.
    - `utilities`: For helper methods.
    - `resources`: For test data and configurations.

### Parallel API Testing
- Implemented using **JUnit** with `@TestMethodOrder` and annotations for structured test execution.

### Dependencies
- **Cucumber-Java** (v7.3.0)
- **Cucumber-JUnit** (v7.3.0)
- **Rest-Assured** (v5.2.1)
- **JavaFaker** (v1.0.2)
- **Jackson-Databind** (v2.13.0)
- **JUnit-Jupiter** (v5.8.2)
- **Lombok** (v1.18.22)

### Automated Test Scenarios
- CRUD operations on `user` endpoint using various HTTP methods (POST, GET, PUT, DELETE).
- **Data-Driven Testing (DDT)** with:
    - JSON files for storing user data.
    - CSV files for credentials.

### Additional Features
- **Assertions** to validate response structure and data using Rest Assured.
- Verifications include status codes, response messages, and data consistency with stored test data.

### Execution Options
- **BDD-Driven Tests**: Use Cucumber runners.
- **Non-BDD Tests**: JUnit-based tests for API functionality with annotations like `@Test`, `@ParameterizedTest`, and `@Order`.

### Report Generation
- Integrated with the **Cucumber Reporting Plugin** for detailed HTML test reports.

## How to Run the Tests
1. Clone the repository to your local machine.
2. Open the project in **IntelliJ IDEA**.
3. Configure the `baseUri` in the configuration file under `src/test/resources`.
4. Execute tests:
    - **For BDD tests**: Run the `CucumberRunner` class.
    - **For JUnit tests**: Run the respective test classes.

## Test Scenarios Covered

### User Management API
- **POST** `/user/createWithList`: Create multiple users.
- **GET** `/user/login`: Login users.
- **GET** `/user/{username}`: Retrieve user details.
- **PUT** `/user/{username}`: Update user information.
- **DELETE** `/user/{username}`: Delete a user.
- **GET** `/user/logout`: Validate user logout.

### Data Validation
- Ensured consistency between API responses and JSON-stored test data.
- Used **Assertions** to verify user data integrity.

## Additional Notes
- The framework supports **parallel execution** and **data-driven testing**, making it scalable and efficient for large datasets.
- JSON files are used to store users for data-driven testing, ensuring reusability and flexibility in test scenarios.
