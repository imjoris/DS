package ds.rug.nl.algorithm;

import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.MulticastDTO;
import ds.rug.nl.threads.CmdMessageHandler;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joris
 */
public class Multicast extends Algorithm {

    Map<Integer, Integer> sequenceNumbersFromNodes;
    
    int myLastSendSeqNr;
    Map<Integer, MulticastDTO> mySendSeqNrs;
    
    public Multicast(CmdMessageHandler mainHandler){
        sequenceNumbersFromNodes = new HashMap<Integer, Integer>();
        mySendSeqNrs = new HashMap<Integer, MulticastDTO>();
        myLastSendSeqNr=0;
    }
    
    @Override
    public void handle(DTO dto) {
        MulticastDTO multidto = (MulticastDTO) dto;
        int nodeId;
        int receivedSeq;

        if (multidto.command == MulticastDTO.cmdType.request) {
                // resend the data sent with the basic multicast
            // with sequence number receivedSeq.
            // i'm not sure yet where this data should be stored, 
            // either networking or node.
        }

        receivedSeq = multidto.getSequencenum();
        Integer mySeqNr = sequenceNumbersFromNodes.get(receivedSeq);

        mySeqNr++;
        if (receivedSeq == mySeqNr) {
            //deliver
        } else if (receivedSeq < mySeqNr) {
            //reject message because it has been delivered before
        } else if (receivedSeq > mySeqNr) {
                    //messages have been missed. send a request to get them
            //send a request to get the messages again
            for (int i = mySeqNr + 1; i < receivedSeq; i++) {
                MulticastDTO resetreq = new MulticastDTO();
                resetreq.setSequencenum(i);
                //network.send(resetreq, multidto.getNodeId(), multidto.getNodeport()); THIS DOES NOT WORK (ANGELO)
            }
        }
    }
    
    public void sendMulticast(DTO data){
        myLastSendSeqNr++;
        String stringData = gson.toJson(data);
        
        MulticastDTO multiDTO = new MulticastDTO(
                stringData, myLastSendSeqNr, MulticastDTO.cmdType.send
        );
        multiDTO.setSequencenum(myLastSendSeqNr);
        multiDTO.jsonDTOData = stringData;
        
        
        mySendSeqNrs.put(myLastSendSeqNr, multiDTO);
    }
}
