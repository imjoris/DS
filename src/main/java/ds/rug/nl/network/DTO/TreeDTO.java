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
public class TreeDTO extends DTO {
    public enum CmdType {
        request,
        reply,
    }

    public final CmdType cmd;
    public final TreeNode<NodeInfo> streamTree;

    public TreeDTO(CmdType cmd, TreeNode<NodeInfo> streamTree) {
        this.cmd = cmd;
        this.streamTree = streamTree;
    }
}
