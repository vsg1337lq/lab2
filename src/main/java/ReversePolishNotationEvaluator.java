import java.util.Stack;

public class ReversePolishNotationEvaluator {

    public static double evaluateRPN(String expression) {
        System.out.println(expression);
        String[] tokens = expression.split(" ");
        Stack<Double> stack = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                if (stack.isEmpty()) {
                    // Handle invalid expression or insufficient operands
                    throw new IllegalArgumentException("Invalid expression or insufficient operands");
                }
                double num2 = stack.pop();
                double num1 = 0;
                if (!stack.isEmpty()) {
                    num1 = stack.pop();
                }

                switch (token) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        if (num1 == 0) {
                            stack.push(-num2);
                        } else {
                            stack.push(num1 - num2);
                        }
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    case "^":
                        stack.push(Math.pow(num1, num2));
                        break;
                    case "sin":
                        stack.push(Math.sin(num2));
                        break;
                    case "cos":
                        stack.push(Math.cos(num2));
                        break;
                    case "tan":
                        stack.push(Math.tan(num2));
                        break;
                    case "cot":
                        stack.push(1 / Math.tan(num2));
                        break;
                    case "ln":
                        stack.push(Math.log(num2));
                        break;
                    case "log10":
                        stack.push(Math.log10(num2));
                        break;
                }
            }
        }

        if (stack.size() != 1) {
            // Handle invalid expression or insufficient operators
            throw new IllegalArgumentException("Invalid expression or insufficient operators");
        }
        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}