/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.Config;
import ds.rug.nl.DNSNode;
import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.network.Networking;
import ds.rug.nl.tree.TreeNode;
import ds.rug.nl.network.DTO.DTO;
import java.util.Iterator;
import ds.rug.nl.threads.CmdMessageHandler;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public abstract class Node extends Thread{

    // tree that stores nodes by their ID
    // kinda ugly that is is here, since we don't want to store the tree for
    // other nodes, just ourselves. Might want to move it somewhere else.

    protected NodeInfo nodeInfo;
    
    protected CmdMessageHandler cmdMessageHandler;
    protected DNSAlgo dnsAlgo;
    public BMulticast multiAlgo;

    protected boolean isRunning;

    public Node(NodeInfo nodeInfo){
        this.nodeInfo = nodeInfo;
        cmdMessageHandler = new CmdMessageHandler();
        network = new Networking();
        isRunning = false;
    }
    
    
    public void keepRunning() {
        isRunning = true;
        while(isRunning){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(DNSNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void execute() {
        //To change body of generated methods, choose Tools | Templates.
    }

    /*########################################
     # GETTERS AND SETTERS
     ##########################################*/
    public int getNodeId() {
        return nodeInfo.nodeId;
    }

    protected Networking network;

    public Networking getNetwork() {
        return network;
    }

    public void setNetwork(Networking network) {
        this.network = network;
    }
    
    public CmdMessageHandler getCmdMessageHandler() {
        return cmdMessageHandler;
    }

    public void setCmdMessageHandler(CmdMessageHandler cmdMessageHandler) {
        this.cmdMessageHandler = cmdMessageHandler;
    }

    public DNSAlgo getDnsAlgo() {
        return dnsAlgo;
    }

    public void setDnsAlgo(DNSAlgo dnsAlgo) {
        this.dnsAlgo = dnsAlgo;
    }

    public InetAddress getIpAddress() {
        return nodeInfo.ipAddress;
    }


    public String getMachineName() {
        return nodeInfo.name;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
