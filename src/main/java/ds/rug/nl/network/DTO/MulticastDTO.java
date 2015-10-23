package ds.rug.nl.network.DTO;

import java.util.ArrayList;

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
    public ArrayList<Integer> requestSeqNums;
    DTO dto;
    
    public ArrayList<Integer> getRequestSeqNums() {
        return requestSeqNums;
    }

    public void setRequestSeqNums(ArrayList<Integer> requestSeqNums) {
        this.requestSeqNums = requestSeqNums;
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

    public MulticastDTO(DTO dto, int sequencenum, cmdType command) {
        this.messagetype = DTO.messageType.multicast;
        this.dto = dto;
        this.sequencenum = sequencenum;
        this.command = command;
    }
    
    public MulticastDTO(){
        requestSeqNums = new ArrayList<Integer>();
        this.messagetype = DTO.messageType.multicast;
    }
    
}
