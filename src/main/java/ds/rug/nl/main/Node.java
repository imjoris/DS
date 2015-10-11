/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.Config;
import ds.rug.nl.network.DTO.JoinDTO;
import static ds.rug.nl.network.DTO.JoinDTO.cmdType.getinfo;
import ds.rug.nl.network.Networking;
import ds.rug.nl.algorithm.TreeNode;
import java.util.Iterator;

/**
 *
 * @author joris
 */
public class Node {
    private String ipAddress = "localhost";
    private String machineName;
    private String id;
    private TreeNode<Node> tree;
    
    Networking network;
    
    public void join(){
        network.send(new JoinDTO(getinfo), Config.dnsip, Config.commandPort);
        
        // at some point a response gives the Tree of the network
        // this tree should be written into variable netTree
        TreeNode<Node> netTree = null;
        Iterator<TreeNode<Node>> highestLeaves = netTree.getHighestLeaves();
        while (highestLeaves.hasNext()){
            TreeNode<Node> leaf = highestLeaves.next();
            if (this.requestAttach(leaf.data))
                leaf.addChild(this);
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
        
        
        // returns whether operation was succesful
        return true;
    }
}
