/*
 * This program is a calculator,
 * with a choice of three types of calculator:
 * for integers, double and strings.
 * The program presents four arithmetic operations:
 * addition, subtraction, multiplication and division.
 * The program used the basic principles of OOP.
 * By Evgeny Bobkunov
 */

// Importing standard libraries
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * The main class, which contains the main methods and runs the program.
 * @author Evgeny Bobkunov
 */
public class Main {

    /**
     * c - is an object of an abstract calculator.
     * Further it acquires the implementation of one of the three types.
     */
    private static Calculator c;
    /**
     * Creating a scanner object to read input data from the console.
     */
    private Scanner scanner = new Scanner(System.in);
    /**
     * In this method, the input data is read and all methods are called.
     * @param args standard parameter of the main method
     */
    public static void main(String[] args) {
        /* Object of the main class is created.
         This object is used to call the methods from other classes. */
        Main main = new Main();
        /* Calling the calculator type reading method.
         The result is placed in the corresponding CalculatorType variable. */
        CalculatorType calculatorType = main.readCalculator();
        // Checking the entered value of the calculator.
        switch (calculatorType) {
            case INTEGER:
                /* If the user chose the integer calculator,
                 Then the object of the IntegerCalculator class is created. */
                c = new IntegerCalculator();
                break;
            case DOUBLE:
                // The same for the double calculator.
                c = new DoubleCalculator();
                break;
            case STRING:
                // The same for the string calculator.
                c = new StringCalculator();
                break;
                /* If the user entered an incorrect value,
                 then the program will be terminated with a warning. */
            default:
                // Call the method to display the fatal error message.
                main.reportFatalError("Wrong calculator type");
                // Exit from the program.
                return;
        }
        // Store the number of arithmetic commands in a variable.
        int numberOfCommands = main.readCommandsNumber();
        // The array in which the entered line with the command is stored.
        String[] strip = new String[numberOfCommands + 1];
        // Filling the array with numberOfCommands lines (1≤numberOfCommands≤50)
        for (int i = 0; i < numberOfCommands + 1; i++) {
            strip[i] = main.scanner.nextLine();
        }
        // Array that will contain split strings (by space) from the array strip.
        String[][] arr = new String[numberOfCommands + 1][];
        // Split input operations by space (" ")
        for (int i = 0; i < numberOfCommands + 1; i++) {
            arr[i] = strip[i].split(" ");
        }
        /* In this loop, we go through all the operations,
         and call the appropriate methods for the c object,
         which has already defined its calculator type. */
        for (int i = 1; i < numberOfCommands + 1; i++) {
            /* arr[i][0] - is the first element of the array,
             It is one of four arithmetic operators: "+, -, *, /" */
            OperationType operationType = main.parseOperation(arr[i][0]);
            // arr[i][1] and arr[i][2] - are the operands of the operation.
            switch (operationType) {
                case ADD:
                    // Call the method add.
                    System.out.println(c.add(arr[i][1], arr[i][2]));
                    break;
                case SUBTRACT:
                    // Call the method subtract.
                    System.out.println(c.subtract(arr[i][1], arr[i][2]));
                    break;
                case MULTIPLY:
                    // Call the method multiply.
                    System.out.println(c.multiply(arr[i][1], arr[i][2]));
                    break;
                case DIVIDE:
                    // Call the method divide.
                    System.out.println(c.divide(arr[i][1], arr[i][2]));
                    break;
                default:
                    /* If none of the performed arithmetic operations is entered,
                     or entered incorrectly. */
                    System.out.println("Wrong operation type");
                    break;
            }
        }
    }

    /**
     * This method reads the type of calculator from the console.
     * @return the type of calculator
     */
    private CalculatorType readCalculator() {
        // Input the type of calculator.
        String calculatorType = scanner.nextLine();
        // Checks the entered data.
        switch (calculatorType) {
            case "INTEGER":
                return CalculatorType.INTEGER;
            case "DOUBLE":
                return CalculatorType.DOUBLE;
            case "STRING":
                return CalculatorType.STRING;
                /* If the entered data is incorrect,
                then method returns INCORRECT calculator type. */
            default:
                return CalculatorType.INCORRECT;
        }
    }

    /**
     * This method reads the number of arithmetic operations from the console.
     * The number must be in the range from 1 to 50.
     * @return the number of arithmetic operations
     */
    private int readCommandsNumber() {
        int n = 0;
        // Catching an exception if something other than numbers is entered.
        try {
            n = scanner.nextInt();
            // If less than 1:
            if (n < 1) {
                reportFatalError("Amount of commands is Not a Number");
            }
            // If not an integer:
        } catch (InputMismatchException e) {
            reportFatalError("Amount of commands is Not a Number");
        }
        // If everything is correct, return the number of operations.
        return n;
    }

    /**
     * This method reports errors and terminates the program.
     * @param err the error message to be displayed on the console before
     * terminating the program.
     */
    private void reportFatalError(String err) {
        System.out.println(err);
        System.exit(0);
    }

    /**
     * This method parses the arithmetic operation.
     * @param operation the arithmetic operation: "+, -, *, /"
     * @return the type of arithmetic operation (OperationType)
     */
    private OperationType parseOperation(String operation) {
        switch (operation) {
            case "+":
                return OperationType.ADD;
            case "-":
                return OperationType.SUBTRACT;
            case "*":
                return OperationType.MULTIPLY;
            case "/":
                return OperationType.DIVIDE;
                // If the entered operation is incorrect, then the method returns INCORRECT.
            default:
                return OperationType.INCORRECT;
        }
    }
}

/**
 * This is an enum class, that contains types of performed calculators.
 */
enum CalculatorType {
    /**
     * INTEGER is the integer calculator.
     */
    INTEGER,
    /**
     * DOUBLE is the double calculator.
     */
    DOUBLE,
    /**
     * STRING is the string calculator.
     */
    STRING,
    /**
     * INCORRECT is the option if the entered calculator type is incorrect.
     */
    INCORRECT
}

/**
 * This is an enum class, that contains types of performed arithmetic operations.
 */
enum OperationType {
    /**
     * ADD is the addition operation.
     * For integer and double calculators this is the sum of two numbers. ( + )
     * For string calculator this is the concatenation of two strings.
     */
    ADD,
    /**
     * SUBTRACT is the subtraction operation.
     * For integer and double calculators this is the difference of two numbers. ( - )
     * For string calculator this operation is unsupported.
     */
    SUBTRACT,
    /**
     * MULTIPLY is the multiplication operation.
     * For integer and double calculators this is the product of two numbers. ( * )
     * For string calculator this is repeating string several times. ( string * number )
     */
    MULTIPLY,
    /**
     * DIVIDE is the division operation.
     * For integer and double calculators this is the quotient of two numbers. ( / )
     * For string calculator this operation is unsupported.
     */
    DIVIDE,
    /**
     * INCORRECT is the option if the entered operation is incorrect.
     */
    INCORRECT
}

/**
 * This is an abstract class Calculator, that contains the main methods of the calculator.
 */
abstract class Calculator {
    /**
     * Abstract method add for addition operation.
     * @param a the first input argument
     * @param b the second input argument
     * @return the result of addition operation
     */
    public abstract String add(String a, String b);
    /**
     * Abstract method subtract for subtraction operation.
     * @param a the first input argument
     * @param b the second input argument
     * @return the result of subtraction operation
     */
    public abstract String subtract(String a, String b);
    /**
     * Abstract method multiply for multiplication operation.
     * @param a the first input argument
     * @param b the second input argument
     * @return the result of multiplication operation
     */
    public abstract String multiply(String a, String b);
    /**
     * Abstract method divide for division operation.
     * @param a the first input argument
     * @param b the second input argument
     * @return the result of division operation
     */
    public abstract String divide(String a, String b);
}

/**
 * This is a class IntegerCalculator, that extends the abstract class Calculator.
 * This class contains methods for integer calculator.
 */
class IntegerCalculator extends Calculator {
    /**
     * This method adds two integers.
     * @param a the first input argument (string that will be converted to integer)
     * @param b the second input argument (string that will be converted to integer)
     * @return the result of addition operation (string)
     */
    @Override
    public String add(String a, String b) {
        // Check if the entered data is correct: Integers.
        try {
            Integer.parseInt(a);
            Integer.parseInt(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        return String.valueOf(Integer.parseInt(a) + Integer.parseInt(b));
    }
    /**
     * This method subtracts two integers.
     * @param a the first input argument (string that will be converted to integer)
     * @param b the second input argument (string that will be converted to integer)
     * @return the result of subtraction operation (string)
     */
    @Override
    public String subtract(String a, String b) {
        try {
            Integer.parseInt(a);
            Integer.parseInt(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        return String.valueOf(Integer.parseInt(a) - Integer.parseInt(b));
    }
    /**
     * This method multiplies two integers.
     * @param a the first input argument (string that will be converted to integer)
     * @param b the second input argument (string that will be converted to integer)
     * @return the result of multiplication operation (string)
     */
    @Override
    public String multiply(String a, String b) {
        try {
            Integer.parseInt(a);
            Integer.parseInt(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        return String.valueOf(Integer.parseInt(a) * Integer.parseInt(b));
    }
    /**
     * This method divides two integers.
     * @param a the first input argument (string that will be converted to integer)
     * @param b the second input argument. Must not be equal to zero.
     * @return the result of division operation (string)
     */
    @Override
    public String divide(String a, String b) {
        // Check if the second argument is not a zero.
        if (Integer.parseInt(b) == 0) {
            return "Division by zero";
        }
        try {
            Integer.parseInt(a);
            Integer.parseInt(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        return String.valueOf(Integer.parseInt(a) / Integer.parseInt(b));
    }
}
/**
 * This is a class DoubleCalculator, that extends the abstract class Calculator.
 * This class contains methods for double calculator.
 */
class DoubleCalculator extends Calculator {
    /**
     * This method adds two doubles.
     * @param a the first input argument (string that will be converted to double)
     * @param b the second input argument (string that will be converted to double)
     * @return the result of addition operation (string)
     */
    @Override
    public String add(String a, String b) {
        try {
            Double.parseDouble(a);
            Double.parseDouble(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        return String.valueOf(Double.parseDouble(a) + Double.parseDouble(b));
    }
    /**
     * This method subtracts two doubles.
     * @param a the first input argument (string that will be converted to double)
     * @param b the second input argument (string that will be converted to double)
     * @return the result of subtraction operation (string)
     */
    @Override
    public String subtract(String a, String b) {
        try {
            Double.parseDouble(a);
            Double.parseDouble(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        return String.valueOf(Double.parseDouble(a) - Double.parseDouble(b));
    }
    /**
     * This method multiplies two doubles.
     * @param a the first input argument (string that will be converted to double)
     * @param b the second input argument (string that will be converted to double)
     * @return the result of multiplication operation (string)
     */
    @Override
    public String multiply(String a, String b) {
        try {
            Double.parseDouble(a);
            Double.parseDouble(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        return String.valueOf(Double.parseDouble(a) * Double.parseDouble(b));
    }
    /**
     * This method divides two doubles.
     * @param a the first input argument (string that will be converted to double)
     * @param b the second input argument. Must not be equal to zero.
     * @return the result of division operation (string)
     */
    @Override
    public String divide(String a, String b) {
        try {
            Double.parseDouble(a);
            Double.parseDouble(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        // Check if the second argument is not a zero.
        if (Double.parseDouble(b) == 0) {
            return "Division by zero";
        }

        return String.valueOf(Double.parseDouble(a) / Double.parseDouble(b));
    }
}

/**
 * Class StringCalculator, that extends the abstract class Calculator.
 * This class contains methods for string calculator.
 */
class StringCalculator extends Calculator {
    /**
     * This method concatenates .
     * @param a the first string
     * @param b the second string
     * @return the result of concatenation operation. A string composed of the first and second.
     */
    @Override
    public String add(String a, String b) {
        return a + b;
    }
    /**
     * Subtracting is an unsupported method for strings.
     * @param a the first string
     * @param b the second string
     * @return the message about unsupported operation for strings.
     */
    @Override
    public String subtract(String a, String b) {
        return ("Unsupported operation for strings");
    }
    /**
     * Multiplying a string by a number.
     * @param a the string that will be repeated
     * @param b the number of repetitions
     * @return string multiplied by a number
     */
    @Override
    public String multiply(String a, String b) {
        // check if b is a positive integer
        try {
            Integer.parseInt(b);
            if (Integer.parseInt(b) < 0) {
                return "Wrong argument type";
            }
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
        // StringBuilder is used for concatenation of strings in a loop.
        StringBuilder result = new StringBuilder();
        // Loop for concatenation of strings b times.
        for (int i = 0; i < Integer.parseInt(b); i++) {
            // Append a string to the result.
            result.append(a);
        }
        return result.toString();
    }
    /**
     * Division of strings is an unsupported method for strings.
     * @param a the first string
     * @param b the second string
     * @return the message about unsupported operation for strings.
     */
    @Override
    public String divide(String a, String b) {
        return ("Unsupported operation for strings");
    }

}
