package ds.rug.nl.network.DTO;

import ds.rug.nl.algorithm.VectorClock;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.tree.TreeNode;

/**
 *
 * @author Bart
 */
public class NodeStateDTO extends DTO {
    public enum CmdType {
        request,
        reply,
    }

    public final CmdType cmd;
    public final TreeNode<NodeInfo> streamTree;
    public final VectorClock bmvc;
    public final VectorClock covc;

    public NodeStateDTO(CmdType cmd, TreeNode<NodeInfo> streamTree, VectorClock bmvc, VectorClock covc) {
        this.cmd = cmd;
        this.streamTree = streamTree;
        this.bmvc = bmvc;
        this.covc = covc;
    }
}
