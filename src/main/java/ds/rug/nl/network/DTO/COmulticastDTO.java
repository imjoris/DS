package ds.rug.nl.network.DTO;

import ds.rug.nl.algorithm.VectorClock;

/**
 *
 * @author joris
 */
public class COmulticastDTO extends DTO {
    private DTO message;
    private VectorClock vClock;

    public COmulticastDTO(VectorClock v, DTO message){
        this.vClock = v;
        this.message = message;
    }
    
    public DTO getMessage() {
        return message;
    }

    public void setMessage(DTO message) {
        this.message = message;
    }

    public void setVectorClock(VectorClock v){
        this.vClock = v;
    }
    
    public VectorClock getVectorClock(){
        return vClock;
    }
    
    public Integer getSender(){
        return this.getNodeId();
    }
    
//    public void setSender(Integer sender){
//        this.sender = sender;
//    }
    
}
