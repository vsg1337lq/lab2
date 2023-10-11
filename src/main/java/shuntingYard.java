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

public class shuntingYard {
    private String inputString;;
    private Stack<String> operatorStack = new Stack<String>();
    private Stack<String> outputStack = new Stack<String>();
    
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
        inputString = input;
    }
    
    public shuntingYard(String initalInput) {
        inputString = initalInput;
    }
}
