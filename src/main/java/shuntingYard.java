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
import java.math.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class shuntingYard {
    private String inputString;;
    private Stack<String> operatorStack = new Stack<String>();
    private Stack<String> outputStack = new Stack<String>();
    
    public shuntingYard(String initalInput) {
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
        inputString = input;
    }
    
    public String checkFunction (int location) {
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
        String[] functions = {"ln", "sin", "cos", "tan", "cot", "log10"}; 
    
        if(token.length() > 1 && token.length() < 5) {
        
            for(int x = 0; x < functions.length; x++) {
                
                if(functions[x].length() > token.length()) {
                    return false;
                }
    
                String subString = token.substring(0, functions[x].length());
    
                Pattern pattern = Pattern.compile(functions[x], Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(subString); // Create a Matcher from the Pattern
    
                if(matcher.find()){
                    return true;
                }
            }
        } else if(token.length() == 1) {
            char c = token.charAt(0);
            
            if (Character.isLetter(c))
                return false;
            else if (Character.isDigit(c)) {
                return true;
            } else {
                String[] operators = {"+", "-", "*", "/", "(", ")", "{", "}", "^"}; 
                
                for(int x = 0; x < operators.length; x++) {
        
                    Pattern pattern = Pattern.compile(operators[x], Pattern.LITERAL);
                    Matcher matcher = pattern.matcher(token); // Create a Matcher from the Pattern
        
                    if(matcher.find()){
                        return true;
                    }
                }
            }
        } 
        return false;
    }
        
    public void convertToRPN () {
        
    }
}
