package ds.rug.nl.main;

import ds.rug.nl.algorithm.VectorClock;
import ds.rug.nl.tree.TreeNode;

/**
 *
 * @author Bart
 */
public class CommonClientData {
    public TreeNode<NodeInfo> streamTree;
    public TreeNode<NodeInfo> thisNode;
    public VectorClock bVector;
    public VectorClock cVector;

    public CommonClientData(NodeInfo thisNode) {
        this.thisNode = new TreeNode(thisNode);
        this.bVector = new VectorClock();
        this.cVector = new VectorClock();
    }    
    
}
