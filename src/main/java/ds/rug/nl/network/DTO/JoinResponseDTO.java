package ds.rug.nl.network.DTO;

import ds.rug.nl.main.NodeInfo;

/**
 *
 * @author Bart
 */
public class JoinResponseDTO extends DTO {
    public enum ResponseType {
        accepted,
        denied
    }
    
    public final ResponseType responseType;
    public final NodeInfo rightNeighbour;

    public JoinResponseDTO(ResponseType responseType, NodeInfo rightNeighbour) {
        this.responseType = responseType;
        this.rightNeighbour = rightNeighbour;
    }
    
}
