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
import ds.rug.nl.algorithm.TreeNode;
import ds.rug.nl.network.DTO.DTO;
import java.util.Iterator;
import ds.rug.nl.threads.CmdMessageHandler;
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

    private TreeNode<String> netTree;

    protected String ipAddress;
    protected String machineName;
    protected String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    protected Networking network;

    public Networking getNetwork() {
        return network;
    }

    public void setNetwork(Networking network) {
        this.network = network;
    }

    protected CmdMessageHandler cmdMessageHandler;
    protected DNSAlgo dnsAlgo;
    public BMulticast multiAlgo;

    protected boolean isRunning;

    //protected abstract void startRunning();

    
    
    public Node(){
        cmdMessageHandler = new CmdMessageHandler();
        network = new Networking();
        isRunning=false;
    }
    
    public void keepRunning() {
        isRunning=true;
        while(isRunning){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(DNSNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void joinTree() {
        // at some point a response gives the Tree of the network
        // this tree should be written into class variable netTree
        Iterator<TreeNode<String>> highestLeaves = netTree.getHighestLeaves();
        while (highestLeaves.hasNext()) {
            TreeNode<String> leaf = highestLeaves.next();
            Node leafNode = getNodeById(leaf.data);
            if (this.requestAttach(leafNode)) {
                leaf.addChild(this.nodeId);
            }
            return;
        }
        // if we get here, none of the leaves were available, so we restart
        // this should be rare, so recursion should rarely happen and it is more
        // legible than a while (true)
        this.joinTree();
    }

    public void execute() {
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attempt to connect as a child to othernode.
     *
     * @param otherNode the node to which connection will be attempted
     * @return whether the operation was succesfull.
     */
    private boolean requestAttach(Node otherNode) {

        throw new UnsupportedOperationException("Not supported yet.");
        // returns whether operation was succesful

    }

    private Node getNodeById(String data) {
        // probably is going to need to deal with the group-manager.
        // May want to move this to another file
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createfunctionmap() {

    }

    /*########################################
     # GETTERS AND SETTERS
     ##########################################*/
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
