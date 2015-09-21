/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author joris-main
 */
public class ClientNode extends Node implements Runnable{

    public ClientNode(String ipAddress, String clientName, int portNumber) {
        super(ipAddress, clientName, portNumber);
        System.out.println(clientName);
    }

     public void run() {
        System.out.println("Client");
         try {
             createClient();
             //receiver(s);
         } catch (IOException ex) {
             Logger.getLogger(ClientNode.class.getName()).log(Level.SEVERE, null, ex);
         }

    }
     
     
     
    
    }
    

