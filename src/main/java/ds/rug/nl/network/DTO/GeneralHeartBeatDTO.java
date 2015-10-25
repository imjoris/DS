package ds.rug.nl.network.DTO;

/**
 *
 * @author angelo
 */
public class GeneralHeartBeatDTO extends DTO {
    public final String beatID;
    public final ReplySend msgType;
    
    public enum ReplySend {
        SEND,
        REPLY
    }

    public GeneralHeartBeatDTO(String beatID, ReplySend msgType) {
        this.beatID = beatID;
        this.msgType = msgType;
    }

}
