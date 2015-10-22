package ds.rug.nl.network.DTO;

import ds.rug.nl.network.VectorClock;

/**
 *
 * @author joris
 */
public class COmulticastDTO extends DTO {
    private DTO message;
    private VectorClock v;
    private String sender;

    public COmulticastDTO(VectorClock v, DTO message, String sender){
        this.v = v;
        this.message = message;
        this.sender = sender;
    }
    
    public DTO getMessage() {
        return message;
    }

    public void setMessage(DTO message) {
        this.message = message;
    }

    public void setVectorClock(VectorClock v){
        this.v = v;
    }
    
    public VectorClock getVectorClock(){
        return v;
    }
    
    public String getSender(){
        return sender;
    }
    
    public void setSender(String sender){
        this.sender = sender;
    }
    
}
