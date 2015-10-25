package ds.rug.nl.network.DTO;

import ds.rug.nl.algorithm.VectorClock;

/**
 *
 * @author joris
 */
public class COmulticastDTO extends DTO {
    private DTO message;
    private VectorClock v;
    private Integer sender;

    public COmulticastDTO(VectorClock v, DTO message, Integer sender){
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
    
    public Integer getSender(){
        return sender;
    }
    
    public void setSender(Integer sender){
        this.sender = sender;
    }
    
}
