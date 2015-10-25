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
