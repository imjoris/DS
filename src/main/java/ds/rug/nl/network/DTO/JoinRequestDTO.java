package ds.rug.nl.network.DTO;

import ds.rug.nl.main.NodeInfo;

/**
 *
 * @author Bart
 */
public class JoinRequestDTO extends DTO{
    public final NodeInfo requestingNode;

    public JoinRequestDTO(NodeInfo nodeInfo) {
        this.requestingNode = nodeInfo;
    }
}
