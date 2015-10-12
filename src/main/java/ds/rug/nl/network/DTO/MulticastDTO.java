package ds.rug.nl.network.DTO;

/**
 *
 * @author joris
 */
public class MulticastDTO extends DTO {
    String message;
    int sequencenum;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSequencenum() {
        return sequencenum;
    }

    public void setSequencenum(int sequencenum) {
        this.sequencenum = sequencenum;
    }
    
}
