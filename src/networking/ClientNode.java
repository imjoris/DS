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
public class ClientNode extends Node implements Runnable {

    /**
     *
     * @param ipAddress
     * @param machineName
     */
    public ClientNode(String ipAddress, String machineName) {
        super(ipAddress, machineName);
        System.out.println(machineName);
    }

    @Override
    public void run() {
        System.out.println("Client");
        try {
            //createTCPClient();
            createUDPClient();
        } catch (IOException ex) {
            Logger.getLogger(ClientNode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
