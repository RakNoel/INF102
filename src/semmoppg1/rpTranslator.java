package semmoppg1;

import java.util.Scanner;
import java.util.Stack;

public class rpTranslator {
    public static void main(String[] args) {
        Stack<String> numqueue = new Stack<>();
        String[] handler = new Scanner(System.in).nextLine().split(" ");

        for (String cr : handler) {
            switch (cr) {
                case "*":
                case "+":
                case "-":
                case "%":
                case "/":
                    String a = numqueue.pop();
                    String b = numqueue.pop();
                    String str = "(" + b + cr + a + ")";
                    numqueue.push(str);
                    break;
                default:
                    numqueue.push(cr);
            }
        }

        System.out.println(numqueue.pop());
    }

}
