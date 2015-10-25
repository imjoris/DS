/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.tree.TreeNode;

/**
 *
 * @author Bart
 */
public class JoinResponseDTO extends DTO {
    public enum ResponseType {
        accepted,
        denied
    }
    
    public TreeNode<NodeInfo> streamTree;
    public ResponseType responseType;

    public JoinResponseDTO(TreeNode<NodeInfo> streamTree, ResponseType responseType) {
        this.streamTree = streamTree;
        this.responseType = responseType;
    }
    
}
