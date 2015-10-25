package ds.rug.nl.network.DTO;

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
