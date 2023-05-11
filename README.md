# Java Unit Testing Tool
## Summary
The Java Unit Testing Tool is a programming tool that provides a graphical user interface (GUI) for executing and visualizing unit tests in Java. It combines all tests annotated with @Test and presents the results in a result-oriented manner.

## Features
* **Database Entry**: The tool allows you to store test information, including test start time, test end time, success/failure status, and reason, in a database of your choice.
* **XML Export**: The tool can also generate XML files containing the test results along with their respective details.
* **Stop/Reset Tests**: You have the ability to stop or reset ongoing tests using the tool's interface.

## Coding Style Principles
When developing your project, it is recommended to follow the following coding style principles:

1. Principle of Encapsulation: Only expose necessary internal details to the outside world.
2. Getter and Setter Methods: Use getter and setter methods for member variables or important class parameters.
3. Refactoring through Submethods: Extract common parts of a method into separate submethods, e.g., getLastSelectedNode().
4. DRY Principle (Don't Repeat Yourself): Avoid duplicating code by extracting common parts into separate methods.
5. Meaningful Names: Choose class, method, and variable names that immediately explain their purpose, functionality, and usage.
6. Use JavaDoc Comments: Document your code using JavaDoc comments, including descriptions, @param, @return, and @throws tags.
7. Method Object Patterns: Extract methods into separate classes to encapsulate their functionality.

## Automated Unit Testing
The tool supports automated unit testing using the following annotations and methods:

* @BeforeClass → beforeTests(): Initializes a transaction system for the tests.
* @Before → setUp(): Sets up a new transaction for each test method.
* @Test → test1(): Test method.
* @After → tearDown(): Destroys the transaction after each test method.
* @AfterClass → afterTests(): Destroys the transaction system after all tests.

Good Cases:

Test a method with valid parameters and expected behavior.
Test the proper functioning of a method under normal conditions, e.g., divide(3, 4) or divide(0, 4).

Edge Cases:

Test a method with invalid or edge-case parameters to check how it handles errors or exceptions, e.g., divide(3, 0) or calls with null as a parameter.
Make sure the methods handle such cases gracefully, such as throwing the appropriate exception.
You can use the JUnit framework to run the tests and obtain the results:

```
Result result = JUnitCore.runClasses(TestClass.class, TRC.class);
for (Failure failure : result.getFailures()) {
    System.out.println(failure.toString());
}
System.out.println(result.wasSuccessful());
```

Use assertEquals() to compare objects based on their contents, and assertTrue() to assert a condition with an optional message.

## Error Handling & Design By Contract
Consider the following principles when it comes to error handling and design by contract:

* Always use a catch block to catch any exception when calling external components or libraries.
* Use catch(Throwable x) to catch any type of error, including Error and Exception.
* Handle errors appropriately, such as displaying error messages or logging them.
* Use ErrorHandler.logError() for logging errors, specifying the error level and other relevant information.
* Use ErrorHandler.Assert to perform assertions and validate conditions. It logs a warning if the condition is not met.

Specify the desired behavior when a condition is not fulfilled:
* When a condition is not fulfilled, you should determine the desired behavior based on whether the condition is true or false. Here are two common approaches:
  * True condition: If the condition is true, the code execution should proceed as expected. No additional action is required.
  * False condition: If the condition is false, you need to define the appropriate action to handle the situation. This could involve logging an error message, throwing an exception, or displaying a warning to the user. Choose the behavior that aligns with the requirements and objectives of your project.
* It is recommended to use assertions or validation checks to verify the conditions at runtime. This helps ensure that the program operates correctly and handles unexpected scenarios gracefully.

Example:
```
public void validate() {
    ErrorHandler.Assert(StringHSType != null && StringHSType.trim().length() > 0, true, Person.class, "Type Error {0}", _personelltype);

    _personelltype = HSPersonalType.valueOf(StringHSType);

    // Specify the desired behavior when the condition is not fulfilled
    if (!_personelltype.isValid()) {
        ErrorHandler.logError(ErrorLevel.WARN, false, Person.class, "Invalid personnel type: {0}", _personelltype);
        // Additional actions or error handling can be implemented here
    }
}
```
In the above example, the validate() method checks if the StringHSType is a valid personnel type. If the condition is false, it logs a warning message indicating an invalid type. You can customize the behavior according to your specific requirements.

Remember to handle such scenarios appropriately to ensure the stability and correctness of your code.

## Multithreading
Use basic thread safety practices in the run method of a thread.

Example:
```
try {
    Thread th = new Thread(new Runnable() {
        public void run() {
            // Perform thread operations here
        }
    });
    th.start();
    th.join();
} catch (InterruptedException e) {
    // Handle the InterruptedException
}
```
Explanation:

* The code creates a new Thread instance th by passing a Runnable implementation to its constructor. The Runnable implementation defines the operations that will be executed by the thread.
* Inside the run() method of the Runnable implementation, you can include the specific operations that need to be executed by the thread.
* The start() method is called on the th thread instance to start the execution of the thread.
* The join() method is invoked on the th thread instance to wait for the completion of the thread execution before proceeding further.
* The join() method can throw an InterruptedException, so it should be surrounded by a try-catch block to handle the exception appropriately.
* Please note that you need to replace the comment "// Perform thread operations here" with the actual code that you want the thread to execute.


# License
This project is licensed under the MIT License.
