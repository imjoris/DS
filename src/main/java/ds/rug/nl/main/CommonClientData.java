/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.tree.TreeNode;

/**
 *
 * @author Bart
 */
public class CommonClientData {
    public TreeNode<NodeInfo> streamTree;
    public TreeNode<NodeInfo> thisNode;  

    public CommonClientData(NodeInfo thisNode) {
        this.thisNode = new TreeNode(thisNode);
    }    
    
}
