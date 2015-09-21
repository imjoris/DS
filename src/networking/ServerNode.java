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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris-main
 */
public class ServerNode extends Node implements Runnable {
    //private Socket s;
    
    public ServerNode() {
        //super(ipAddress);
    }

    public void run() {
        System.out.println("Server");

        try {
            Socket s = createServer();
            sender(s);
        } catch (IOException ex) {
            Logger.getLogger(ServerNode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }

    

}

