/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Vincent
 */

import java.io.*;
import java.util.Scanner; 
public class calculatorMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Test");
        String inputValues = "sin(0)";
       /* 
        shuntingYard test = new shuntingYard(inputValues);
        
        test.toRPN();
        String output = test.getOutput();
        System.out.println(output);
        
        //ReversePolishNotationEvaluator evaluator = new ReversePolishNotationEvaluator();
        //System.out.println(evaluator.evaluateRPN(output));
        */
        
        
        shuntingYard test = new shuntingYard(inputValues);
        test.toRPN();
        ReversePolishNotationEvaluator evaluator = new ReversePolishNotationEvaluator();
        
        try {
            String localDir = System.getProperty("user.dir");
            File myObj = new File(localDir + "\\src\\main\\java\\inputTest.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                test.newInput(data);
                test.toRPN();
                
                String output = test.getOutput();
                System.out.println(output);
                //System.out.println(evaluator.evaluateRPN(output));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
    } 
    
}
