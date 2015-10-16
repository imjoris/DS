/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.network.Networking;
import ds.rug.nl.algorithm.TreeNode;
import ds.rug.nl.network.DTO.DTO;
import java.util.Iterator;
import ds.rug.nl.threads.CmdMessageHandler;

/**
 *
 * @author joris
 */
public class Node {
    // tree that stores nodes by their ID
    // kinda ugly that is is here, since we don't want to store the tree for
    // other nodes, just ourselves. Might want to move it somewhere else.
    private TreeNode<String> netTree;
    
    protected String ipAddress = "localhost";
    protected String machineName;
    protected String id;
    
    protected Networking network;
    
    protected CmdMessageHandler cmdMessageHandler;
    protected DNSAlgo dnsAlgo;
    
    public Node() {
        cmdMessageHandler = new CmdMessageHandler();
        
        //register the dnsAlgorithm with the cmd message handler
        //this way, all the dns message types get handled by the
        //dnsAlgo class
        dnsAlgo = new DNSAlgo();
        cmdMessageHandler.registerAlgorithm(DTO.messageType.dns, dnsAlgo);
        
        network = new Networking();
    }
    
    public void join(){
        // at some point a response gives the Tree of the network
        // this tree should be written into class variable netTree
        Iterator<TreeNode<String>> highestLeaves = netTree.getHighestLeaves();
        while (highestLeaves.hasNext()){
            TreeNode<String> leaf = highestLeaves.next();
            Node leafNode = getNodeById(leaf.data);
            if (this.requestAttach(leafNode))
                leaf.addChild(this.id);
                return;
        }
        // if we get here, none of the leaves were available, so we restart
        // this should be rare, so recursion should rarely happen and it is more
        // legible than a while (true)
        this.join();  
    }
    public void execute() {
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attempt to connect as a child to othernode.
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
    
    private void createfunctionmap(){
        
    }
}
