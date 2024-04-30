- Describe in your own words what is z-score and Min-Max scaling and why would we need to use such normalization methods? describe by example, if possible.
```
Z-score normalization and Min-Max scaling are techniques to adjust data ranges. Z-score makes data's mean 0 and standard deviation 1, which is good for handling outliers but doesn't make all features the same scale. Min-Max scaling adjusts data to a 0-1 range, ensuring all features are on the same scale, but it doesn't handle outliers well. We use these methods to make machine learning algorithms work better by ensuring all input features are on a similar scale, which can improve model accuracy and reduce the impact of outliers

Z-score Normalization Example:

Imagine you have a dataset of student test scores ranging from 50 to 100. The mean (average) score is 75, and the standard deviation is 15.

Original Score: 85
Z-score = (85 - 75) / 15 = 1
After Z-score normalization, the score of 85 becomes 1, indicating it's one standard deviation above the mean.

Min-Max Scaling Example:

Using the same dataset of student test scores ranging from 50 to 100, where the minimum score is 50 and the maximum score is 100.

Original Score: 85
Min-Max Score = (85 - 50) / (100 - 50) = 0.85
After Min-Max scaling, the score of 85 becomes 0.85, indicating it's 85% of the way between the minimum and maximum scores 

```

- We used Junit5 in our test cases, describe in your own words why would we use such library? 
what is unit testing and why would we use it?
```
We use JUnit 5 in our test cases for several reasons:

- Improved Features and Flexibility: JUnit 5 offers new features and improvements over JUnit 4, making tests more powerful, easier to maintain, and better organized. It supports Java 8 features like lambda expressions, which can make tests more concise and readable 14.

- Better Test Descriptions and Organization: JUnit 5 allows for better display names for tests and the ability to organize tests hierarchically, making it easier to understand and manage test cases 1.

- Multiple Libraries: JUnit 5 is organized into multiple libraries, so you can import only the features you need, keeping your project lightweight and efficient 1.

- Nested Tests: JUnit 5 introduces nested tests, which are easier to set up and maintain, and they better describe the relationships between test groups, improving test design and readability 1.

Unit Testing is the process of testing individual components of a software application to ensure they work as expected. It's crucial for several reasons:

- Early Bug Detection: Unit tests help catch bugs early in the development process, before they become more costly to fix.

- Improved Code Quality: Writing unit tests encourages developers to write cleaner, more modular code, as they need to ensure each component works in isolation.

- Faster Development: By catching bugs early, unit tests can speed up the development process by reducing the time spent on debugging and fixing issues.

- In summary, using JUnit 5 for unit testing in our projects allows us to leverage its advanced features for more effective and efficient testing, leading to higher code quality and faster development cycles
```
- What is the IDE did you use?
```
 Eclipse 
```

- Name the Java APIs you used?
```
- JUnit Jupiter API
- Java I/O API
```

- Mention down any difficulties or concerns you faced while working on the task. Your feedback is appreciated :D.
```
I didn't encounter any difficulties while working on the task. It was actually quite enjoyable and I found it to be a fun exercise.
```

- If you implement any of the bonus points, please mentioned it in this file
```
- added 4 new cases in unit testing
```

- Did this task enrich technical skills or knowledge? If yes, tell us how: 
```

Yes, this task definitely enriched my technical skills and knowledge. It allowed me to practice writing unit tests using JUnit 5, which improved my understanding of testing methodologies and best practices. Additionally, implementing normalization methods such as z-score and Min-Max scaling deepened my understanding of data preprocessing techniques commonly used in machine learning and data analysis tasks. Overall, this task provided valuable hands-on experience that enhanced my proficiency in Java programming and software testing.
```
