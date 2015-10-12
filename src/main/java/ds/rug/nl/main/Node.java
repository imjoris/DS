/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.Config;
import ds.rug.nl.network.Networking;
import ds.rug.nl.threads.CmdMessageHandler;
import java.io.IOException;

/**
 *
 * @author joris
 */
public class Node {
    protected String ipAddress = "localhost";
    protected String machineName;
    protected String id;
    
    protected Networking network;
    
    protected CmdMessageHandler commandReceiver;
    //MulticastReceiver multicastReceiver;
    public Node() {
        network = new Networking();
        this.network.startReceiving(Config.commandPort, commandReceiver);
        //this.multicastReceiver = new MulticastReceiver(network);
    }
    
    public void execute() {
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @throws IOException
     */
    protected void setIp() throws IOException{
    }
    
    private void createfunctionmap(){
        
    }
}
