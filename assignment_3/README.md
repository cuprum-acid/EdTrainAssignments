# Assignment 3

Alphabetical sorting of names from a text file.


- input: **standard input**
- output: **standard output**


## Assignment description 
#### (From [codeforces.com](https://codeforces.com/))

In this assignment you will need to design a family of calculators using OOP principles. The UML class diagram demonstrates the minimal set of classes which you should implement, you can extend this functionality as you wish. You are NOT allowed to modify the names or signatures of UML diagram classes, methods and fields.

![The Assignment UML class diagram.](/images/picture.png "The Assignment UML class diagram.")

The first line of standard input should contain one of three words: `DOUBLE`, `INTEGER`, `STRING`. The next line will contain the integer number **_N (1≤N≤50)_** defining the total amount of commands. After that, the input will <ins>always</ins> contain exactly **_N_** lines with commands calculator should perform:
* The "+" command means the calculator should either sum up two numbers or concatenate two strings, and print the result to the output.
* The "−" command means the calculator should either subtract two numbers and print the result to the output, or print `"Unsupported operation for strings"`.
* The "∗" command means calculator should either multiply two numbers and print the result to the screen, or for strings, it should repeat the first argument amount of times given in the second argument (a positive integer number), otherwise print `"Wrong argument type"`.
* The "/" command means the calculator should either divide two numbers and print the result to the output or print `"Unsupported operation for strings"`.

Any operation for `DOUBLE` or `INTEGER` calculators that will contain inapplicable symbols as operands, should print `"Wrong argument type"` and continue work with the next operation.

For any other inapplicable operation, the message `"Wrong operation type"` should be printed to the output.

In case of wrong calculator type on the first line, the output should contain only `"Wrong calculator type"`. In case when the specified number of commands is incorrect, the output should contain only `"Amount of commands is Not a Number"` and exit from the program.

In case when the calculator tries to divide by zero, the output should contain only `"Division by zero"` and continue work with the next operation.

#### **Examples**
>**input**
>  ``` 
> DOUBLE
> 3
> + 1 1.5
> - 0 8
> / 1 3
> ```
>**output**
>  ``` 
> 2.5
> -8.0
> 0.3333333333333333
> ```

### Note
Make sure to insert one newline ("`\n`") character after each line written to the standard output.

For this assignment you need to work with console for reading inputs and printing outputs, not with files.

## Authors

- **Evgeny Bobkunov** - *Wrote a program and issued a README file*
  - Email: [e.bobkunov@innopolis.university](mailto:e.bobkunov@innopolis.university) 