/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Vincent
 */


public class calculatorMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Test");
        String inputValues = "5+5";
        
        shuntingYard test = new shuntingYard(inputValues);
        
        test.toRPN();
        
        String outputTest = test.getOutput();
        System.out.println(outputTest);
    }
    
}
