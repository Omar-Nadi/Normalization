# Data Normalization Project

## Introduction

This project is a Java-based implementation of data normalization techniques, specifically Z-score and Min-Max scaling. It includes a `NormalizerImpl` class that implements the `Normalizer` interface, a `ScoringSummaryImpl` class that implements the `ScoringSummary` interface, and a comprehensive unit test class to ensure the functionality of the implemented methods.

## Required setup
- Java 8+
- Maven 3.6+
- A Java IDE like Eclipse, NetBeans, or IntelliJ.

## Project Setup

1. **Clone the Repository**: Clone the project repository to your local machine.

2. **Install Dependencies**: Ensure you have Java SE installed on your machine. This project does not require any additional libraries.

3. **Build the Project**: Navigate to the project directory in your terminal and run `mvn clean install` to build the project and run all tests.

## Understanding Data Normalization

Data normalization is a preprocessing technique used to standardize the range of independent variables or features of a dataset. It ensures that data values are on a similar scale, which helps in improving the performance and stability of machine learning algorithms.

### Z-Score Normalization

Z-score normalization, also known as standardization, transforms data values into a standard normal distribution with a mean of 0 and a standard deviation of 1. It is calculated by subtracting the mean of the dataset from each data point and then dividing by the standard deviation.

The formula for Z-score normalization is:

\[ z = \frac{x - \mu}{\sigma} \]

Where:
- \( z \) is the standardized value (Z-score)
- \( x \) is the original value
- \( \mu \) is the mean of the dataset
- \( \sigma \) is the standard deviation of the dataset

### Min-Max Scaling

Min-Max scaling, also known as normalization, rescales data values to a fixed range, typically between 0 and 1. It is calculated by subtracting the minimum value from each data point and then dividing by the difference between the maximum and minimum values.

The formula for Min-Max scaling is:

\[ x' = \frac{x - \text{min}(X)}{\text{max}(X) - \text{min}(X)} \]

Where:
- \( x' \) is the scaled value
- \( x \) is the original value
- \( \text{min}(X) \) is the minimum value of the dataset
- \( \text{max}(X) \) is the maximum value of the dataset

## Building and Running the Project

1. **Building**: Execute `mvn clean install` in the project directory. This command cleans the project, compiles the source code, runs tests, and installs the package into the local repository.

2. **Running Unit Tests**: Execute `mvn test` to run all unit tests. Ensure all tests pass before proceeding.

## Unit Testing

The project includes a comprehensive suite of unit tests to validate the functionality of the `NormalizerImpl` class and its methods. The tests cover various scenarios, including:

- **Invalid Input Handling**: Tests that ensure the application correctly handles invalid inputs, such as non-existent files or columns not found in the CSV files.
- **Z-score Normalization**: Tests that validate the Z-score normalization process for both marks and salary columns in sample CSV files.
- **Min-Max Scaling**: Tests that verify the Min-Max scaling process for both marks and salary columns in sample CSV files.

### Understanding Test Cases

The unit tests are designed to cover a wide range of scenarios, including:

- **Exception Handling**: Tests that verify the application throws the correct exceptions when encountering invalid inputs.
- **Normalization Process**: Tests that validate the normalization process by comparing the output of the normalized data with expected results.

Each test case is designed to be self-contained, ensuring that the tests are independent and can be run in any order.

## Conclusion

This project demonstrates the application of Z-score and Min-Max scaling techniques for data normalization in Java. By adhering to best practices for unit testing and ensuring all tests pass, the project provides a robust and reliable solution for data normalization tasks.
