package ds.rug.nl.network.DTO;

/**
 *
 * @author angelo
 */
public class ElectionReplyDTO extends DTO {
    private int id;
    private int phaseNumber;

    public ElectionReplyDTO(int id, int phaseNumber) {
        super();
        this.id = id;
        this.phaseNumber = phaseNumber;
    }
    
    
    
}
