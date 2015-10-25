/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.JoinRequestDTO;
import ds.rug.nl.network.DTO.JoinResponseDTO;
import static ds.rug.nl.network.DTO.JoinResponseDTO.ResponseType.*;
import ds.rug.nl.tree.TreeNode;

/**
 *
 * @author Bart
 */
public class JoinAlgo extends Algorithm {
    
    private TreeNode<NodeInfo> streamTree;
    private TreeNode<NodeInfo> thisNode;

    public JoinAlgo(Node node, TreeNode<NodeInfo> streamTree, TreeNode<NodeInfo> thisNode) {
        super(node);
        this.streamTree = streamTree;
        this.thisNode = thisNode;
    }

    @Override
    public void handle(DTO message) {
        if (message instanceof JoinRequestDTO)
            handleRequest((JoinRequestDTO) message);
        else if (message instanceof JoinResponseDTO)
            handleResponse((JoinResponseDTO) message);
        
    }

    // VERY VERY WRONG!!!!!!!!!!!
    // WILL *NOT* deal at all with rejection
    private void handleResponse(JoinResponseDTO message) {
        streamTree = message.streamTree;
        thisNode = streamTree.addChild(node.getNodeInfo());
    }

    // TO-DO make this not crash when the node still has no tree
    private void handleRequest(JoinRequestDTO message) {
        JoinResponseDTO response;
        if (this.fullNode())
            response = new JoinResponseDTO(null, denied);
        else { 
            this.addChild(message.nodeInfo);
            response = new JoinResponseDTO(streamTree, accepted);
        }        
        reply(response, message); 
    }

    private boolean fullNode() {
        synchronized (thisNode) {
            return thisNode.children.size() >= 3;
        }
    }

    private void addChild(NodeInfo nodeInfo) {
        synchronized (thisNode) {
            thisNode.addChild(nodeInfo);
        }
    }


    
}
