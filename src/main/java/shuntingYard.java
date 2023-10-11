/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Vincent
 */

import java.util.Stack;
import java.util.regex.Matcher;
import java.lang.Character; 
import java.util.regex.Pattern;

public class shuntingYard {
    private String inputString;;
    private Stack<String> operatorStack = new Stack<String>();
    private Stack<String> outputStack = new Stack<String>();
    
    public shuntingYard(String initalInput) {
        initalInput = initalInput.toLowerCase();
        inputString = initalInput;
    }
    
    public boolean isOperatorsEmpty(){
        return operatorStack.empty();
    }
    
    public boolean isOutputEmpty(){
        return outputStack.empty();
    }
    
    public String operatorPop(){
        return operatorStack.pop();
    }
    
    public String outputPop(){
        return outputStack.pop();
    }   
    
    public void operatorPush(String token){
        operatorStack.push(token);
    }
    
    public void outputPush(String token){
        outputStack.push(token);
    }
    
    public String operatorPeek(){
        return operatorStack.peek();
    }
    
    public void addInputString(String input){
        input = input.toLowerCase();
        inputString = input;
    }
    
    public String returnFunction (int location) {
        String[] functions = {"log10", "sin", "cos", "tan", "cot", "ln"}; 
        
        for(int x = 0; x < functions.length; x++) {
            
            String subString = inputString.substring(location, location + functions[x].length());
            
            Pattern pattern = Pattern.compile(functions[x], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(subString); // Create a Matcher from the Pattern
            
            if(matcher.find()){
                return functions[x];
            } 
        }
        //invalid characters
        return null;
    }
    
    public boolean tokenChecker (String token) {
    
        String[] testExpressions = {"log10", "sin", "cos", "tan", "cot", "ln", "+", "-", "*", "/", "(", ")", "{", "}", "^"}; 

        for(int x = 0; x < testExpressions.length; x++) {
            
            if(token.length() < testExpressions[x].length()) {
                continue;
            } else {

                String subString = token.substring(0, testExpressions[x].length());
    
                Pattern pattern = Pattern.compile(testExpressions[x], Pattern.LITERAL);
                Matcher matcher = pattern.matcher(subString); 
    
                if(matcher.find()){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean tokenChecker (int location) {
    
        String[] testExpressions = {"log10", "sin", "cos", "tan", "cot", "ln", "+", "-", "*", "/", "(", ")", "{", "}", "^"}; 

        for(int x = 0; x < testExpressions.length; x++) {

            String subString = inputString.substring(0, location + testExpressions[x].length());

            Pattern pattern = Pattern.compile(testExpressions[x], Pattern.LITERAL);
            Matcher matcher = pattern.matcher(subString); 

            if(matcher.find()){
                return true;
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
                return testExpressions[x];
            }
        }
        
        return null;
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
            
            if(Character.isDigit(token.charAt(0)) == true) {
                outputStack.push(token);
            } else if (returnToken(i) != null) {
                String currToken = returnToken(i);
                
                if(operatorStack.isEmpty()){
                    operatorStack.push(currToken);
                } else {
                    String operatorStackTop = operatorStack.peek();
                    
                    if(currToken == "(" || currToken == "{") {
                        operatorStack.push(currToken);
                    } else if (currToken == ")" || currToken == "}"){
                        while(operatorStack.peek() != "(" || operatorStack.peek() != "{"){
                            outputStack.push(operatorStack.pop());
                        }
                        operatorStack.pop();
                    } else {
                        if(getPrecedence(currToken) <  getPrecedence(operatorStackTop)) {
                            outputStack.push(operatorStack.pop());
                        } else {
                            operatorStack.push(currToken);
                        }
                    }
                }
            
            } else {
                //invalid character
            }
        }
        while(operatorStack.isEmpty() == false){
            outputStack.push(operatorStack.pop());
        }
        
    }
    
    public String getOutput (){
        String outputString = outputStack.pop();
        while (!outputStack.isEmpty()) {
            String concatString = outputStack.pop();
            outputString = outputString.concat(concatString);
        }
          return outputString;
    }
}
