package ds.rug.nl.main;

import ds.rug.nl.Config;
import ds.rug.nl.network.Networking;
import ds.rug.nl.threads.CmdMessageHandler;

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
    
    private void createfunctionmap(){
        
    }
    
    public String getIpAddress(){
        return null;
    }
    
    public void setIpAddress(String ip){
        
    }
    
    public String getMachineName(){
        return null;
    }
    
    public void setMachineName(String name){
        
    }
    
    public String getID(){
        return null;
    }
    
    public void setID(String id){
        
    }
}
