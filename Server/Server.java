/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author duy21
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            ServerSocket server = new ServerSocket(9999);
            System.out.println("Server started");
            while(true){
                Socket socket = server.accept();
                System.out.println("co ket noi");
                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                String us = dis.readUTF();
//                String ls = dis.readUTF();
//                us = us.toUpperCase();
//                ls = ls.toLowerCase();
//                System.out.println(us);
//                System.out.println(ls);
                String expression = dis.readUTF();
                ArrayList<String> queue = InfixToPostFix(expression);
                double result = Calculator(queue);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                dos.writeUTF(us);
//                dos.writeUTF(ls);
                dos.writeUTF(result + "");
            }
        } catch (Exception e) {
            System.err.println(" Connection Error: " + e);
        }
    }
    public static ArrayList InfixToPostFix(String s) {
        String regex = "\\d+|\\+|\\*|/|-|\\(|\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        ArrayList<String> stack = new ArrayList();
        ArrayList<String> queue = new ArrayList();
        while(matcher.find()){
            String str = matcher.group();
            if (isNumber(str)){
                queue.add(str);
            } else {
                if (str.equals("(")) {
                    stack.add(str);
                } else {
                    if (str.equals(")")) {
                        while (!stack.get(stack.size() - 1).equals("(")) {
                            queue.add(stack.remove(stack.size() - 1));
                        }
                        stack.remove(stack.size() - 1);
                    } else {
                        while (!stack.isEmpty() && Precedence(str) <= Precedence(stack.get(stack.size() - 1))) {
                            queue.add(stack.remove(stack.size() - 1));
                        }
                        stack.add(str);
                    }
                }
            }
        }
        for (int i = stack.size() - 1; i >= 0; i-- ) {
            queue.add(stack.get(i));
        }
        for (String item : queue) {
           System.out.println(item);
        }
        return queue;
    }
    
    public static double Calculator (ArrayList<String> queue) {
        //TODO
        double result = 0;
        ArrayList<Double> stack = new ArrayList();
        while(queue.size() > 0) {
            String first = queue.remove(0);
            if(isNumber(first)){
                stack.add(Double.parseDouble(first));
            } else {
                try {
                    double num2 = stack.remove(stack.size() - 1);
                    double num1 = stack.remove(stack.size() - 1);
                    switch(first) {
                        case "+":
                            stack.add((num1 + num2));
                            break;
                        case "-":
                            stack.add((num1 - num2));
                            break;
                        case "*":
                            stack.add((num1 * num2));
                            break;
                        case "/":
                            stack.add((num1 / num2));
                            break;
                    }    
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        result = stack.remove(0);
        return result;
    }
    
    public static boolean isNumber(String s) {
        try {
            int number = Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    } 
    public static int Precedence(String a) {
        if (a.equals("(")){
            return 0;
        } else if ((a.equals("+") || a.equals("-"))) {
            return 1;
        } else if((a.equals("*") || a.equals("/"))){
            return 2;
        } else {
            return 3;
        } 
    }
}
