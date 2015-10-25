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
    
    public ResponseType responseType;

    public JoinResponseDTO(ResponseType responseType) {
        this.responseType = responseType;
    }
    
}
