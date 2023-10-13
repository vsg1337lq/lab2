/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Vincent
 */

import java.util.Stack;
import java.lang.Character; 
import java.util.Queue;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class shuntingYard {
    private String inputString;;
    private Stack<String> operatorStack = new Stack<String>();
    private Queue<String> output = new LinkedList<String>();
    private boolean log10_SET = false;
    private boolean invalidInput = false;
    
    public shuntingYard(String initalInput) {
        initalInput = initalInput.toLowerCase();
        inputString = initalInput;
    }
    
    public boolean isOperatorsEmpty(){
        return operatorStack.empty();
    }
    
    public String operatorPop(){
        return operatorStack.pop();
    }
    
    public void operatorPush(String token){
        operatorStack.push(token);
    }
    
    public String operatorPeek(){
        return operatorStack.peek();
    }
    
    public void addInputString(String input){
        input = input.toLowerCase();
        setInvalidInputStatus(false);
        inputString = input;
    }
    
    public boolean getInvalidInputStatus (){
        return invalidInput;
    }
    
    public void setInvalidInputStatus (boolean status) {
        invalidInput = status;
    }
    
    public void setLog10 (){
        log10_SET = true;
    }
    
    public void resetLog10() {
        log10_SET = false;
    }
    
    public boolean getLog10_SET (){
        return log10_SET;
    }
    
    public void newInput(String newIput) {
        resetLog10();
        newIput = newIput.toLowerCase();
        inputString = newIput;
    }
    
    public String returnFunction (int location) {
        String[] functions = {"log10", "sin", "cos", "tan", "cot", "ln"}; 
        
        for(int x = 0; x < functions.length; x++) {
            
            String subString = inputString.substring(location, location + functions[x].length());
            
            Pattern pattern = Pattern.compile(functions[x], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(subString); // Create a Matcher from the Pattern
            
            if(matcher.find()){
                if(x == 0){
                    setLog10();
                }
                return functions[x];
            } 
        }
        //invalid characters
        return null;
    }
    
    public boolean tokenChecker (String token) {
    
        String[] testExpressions = {"log10", "sin", "cos", "tan", "cot", "ln", "+", "-", "*", "/", "(", ")", "{", "}", "^"}; 
        
                // Add a specific case for unary '-'
        if (token.equals("-") && (operatorStack.isEmpty() || operatorStack.peek().equals("("))) {
            return true;
        }
        
        // Error checking for consecutive operators
        if (token.length() > 1) {
            for (int i = 0; i < token.length() - 1; i++) {
                if (isOperator(token.charAt(i)) && isOperator(token.charAt(i + 1))) {
                    return false;
                }
            }
        }

        for(int x = 0; x < testExpressions.length; x++) {
            
            if(token.length() < testExpressions[x].length()) {
                continue;
            } else {

                String subString = token.substring(0, testExpressions[x].length());
    
                Pattern pattern = Pattern.compile(testExpressions[x], Pattern.LITERAL);
                Matcher matcher = pattern.matcher(subString); 
    
                if(matcher.find()){
                    if(x == 0){
                        setLog10();
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public String returnToken (int location) {
        String[] testExpressions = {"log10", "sin", "cos", "tan", "cot", "ln", "+", "-", "*", "/", "(", ")", "{", "}", "^"}; 

        for(int x = 0; x < testExpressions.length; x++) {
            
            if(location + testExpressions[x].length() > inputString.length()) {
                continue;
            }
            
            

            String subString = inputString.substring(location, location + testExpressions[x].length());

            Pattern pattern = Pattern.compile(testExpressions[x], Pattern.LITERAL);
            Matcher matcher = pattern.matcher(subString); 

            if(matcher.find()){
                if(x == 0){
                        setLog10();
                }
                return testExpressions[x];
            }
        }
        
        return null;
    }
    
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
   
    public int getPrecedence(String token){
        
        if(token == "/" || token == "*") {
            return 2;
        } else if (token == "+" || token == "-"){
            return 1;
        } else if (token == "^") {
            return 3;
        } else {
            return -1;
        }
    } 
    
    //copied from https://www.geeksforgeeks.org/java-program-to-implement-shunting-yard-algorithm/#
    //im not sure if could have came up with something different anyways
    public boolean getAssociativity (String token) {
        if (token == "+" || token == "-" || token == "/" || token == "*") {
            return true;
        } else {
            return false;
        }
    }
        
    public void toRPN () {
        for(int i = 0; i < inputString.length(); i++) {
            String token = inputString.substring(i, i+1);

            // Handle negative numbers or negative operators
            if (token.equals("-") && (i == 0 || inputString.charAt(i - 1) == '(')) {
                token = "_"; // Change '-' to '_' to denote unary minus
            }
            
            //checks for digits and then pushes them onto the stack
            if(Character.isDigit(token.charAt(0)) == true) {
                int start = i;
                int current = i; // separate variable for iteration
                if (getLog10_SET() == false){
                    //ensure the entire number added to the stack i.e. 100,223
                    while (current < inputString.length()) {
                        if (Character.isDigit(inputString.charAt(current))) {
                                current++;
                        } else {
                            break;
                        }
                    }
                    output.add(inputString.substring(start, current));
                    i = current - 1; // update the original variable 'i' to the value of 'current'
                } else {
                    i++;
                    resetLog10();
                }
            } else if (returnToken(i) != null) {
                String currToken = returnToken(i);
                    
                if(operatorStack.isEmpty()){
                    operatorStack.push(currToken);
                } else {
                    String operatorStackTop = operatorStack.peek();
                    
                    //check for opening brackets or parethesis at the end of an expression
                    if(inputString.length() < i && currToken.equals("{") || currToken.equals("{")) {
                        setInvalidInputStatus(true);
                        break;
                    } else {
                        if(currToken == "(" || currToken == "{") {
                            operatorStack.push(currToken);
                        } else if (currToken == ")"){
                            while(operatorStack.peek() != "("){

                                    output.add(operatorStack.pop());
                            }

                            if (operatorStack.peek() == "(") {
                                operatorStack.pop();
                            }
                        } else if (currToken == "}"){
                            while(operatorStack.peek() != "{"){

                                    output.add(operatorStack.pop());
                            }

                            if (operatorStack.peek() == "{") {
                                operatorStack.pop();
                            }
                        }else {
                            if(getPrecedence(currToken) <  getPrecedence(operatorStackTop)) {
                                output.add(operatorStack.pop());
                            } else {
                                operatorStack.push(currToken);
                            }
                        }
                    }
                }
            } 
        }
        if(getInvalidInputStatus() == false){
            while(operatorStack.isEmpty() == false){
                output.add(operatorStack.pop());
            }   
        }
    }
   
    
    public String getOutput (){
       if(getInvalidInputStatus() == false){ 
            String outputString = output.poll();
            while (!output.isEmpty()) {
                String concatString = output.poll();
                outputString = outputString.concat(" ");
                outputString = outputString.concat(concatString);
            }
              return outputString;
       } else {
           return "invalid input";
       }
    }
}
