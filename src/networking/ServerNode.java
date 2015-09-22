/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris-main
 */
public class ServerNode extends Node implements Runnable {
    //private Socket s;

    /**
     *
     * @param machineName
     */
    public ServerNode(String machineName) {
        super(machineName);
    }

    @Override
    public void run() {
        System.out.println("Server");
        try {
            //createTCPServer();
            createUDPServer();
        } catch (IOException ex) {
            Logger.getLogger(ServerNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
