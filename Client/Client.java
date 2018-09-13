/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author duy21
 */
public class Client {
    public static void main(String args[]){
        try {
            Socket client = new Socket("localhost", 9999);
            System.out.println("Client gui ket noi!");
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF("5+1+2");
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String response = dis.readUTF();
            System.out.println(response);
//            System.out.println(response.length());
            client.close();
        } catch (Exception e) {
            System.err.println(" Connection Error: " + e);
        }
    }
}
