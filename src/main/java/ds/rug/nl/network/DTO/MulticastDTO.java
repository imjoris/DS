package ds.rug.nl.network.DTO;

import ds.rug.nl.algorithm.DTOSeq;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author joris
 */
public class MulticastDTO extends DTO {
    public enum cmdType{
        request,
        response,
        send
    }
    public int sequencenum;
    public ArrayList<Integer> requestSeqNums;
q    public LinkedList<DTOSeq> responseDTOseq;
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
        this.dto = dto;
        this.sequencenum = sequencenum;
        this.command = command;
    }
    
    public MulticastDTO(){
        requestSeqNums = new ArrayList<Integer>();
    }
    
}
