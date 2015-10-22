package ds.rug.nl.network.DTO;

/**
 *
 * @author joris
 */
public class MulticastDTO extends DTO {
    public enum cmdType{
        request,
        send
    }
    public String jsonDTOData;
    public int sequencenum;

    public String getJsonDTOData() {
        return jsonDTOData;
    }

    public void setJsonDTOData(String jsonDTOData) {
        this.jsonDTOData = jsonDTOData;
    }

    public int getSequencenum() {
        return sequencenum;
    }

    public void setSequencenum(int sequencenum) {
        this.sequencenum = sequencenum;
    }

    public cmdType getCommand() {
        return command;
    }

    public void setCommand(cmdType command) {
        this.command = command;
    }
    public cmdType command;

    public MulticastDTO(String jsonDTOData, int sequencenum, cmdType command) {
        this.messagetype = DTO.messageType.multicast;
        this.jsonDTOData = jsonDTOData;
        this.sequencenum = sequencenum;
        this.command = command;
    }
    
    
    public MulticastDTO(){
        this.messagetype = DTO.messageType.multicast;
    }
    
}
