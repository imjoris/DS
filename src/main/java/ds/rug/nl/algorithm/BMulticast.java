package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.MulticastDTO;
import ds.rug.nl.threads.CmdMessageHandler;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author joris
 */
public class BMulticast extends Algorithm {

    class DTOSeq{
        
        public int sequenceNumber;
        public DTO dto;

        public DTOSeq(int sequenceNumber, DTO dto) {
            this.sequenceNumber = sequenceNumber;
            this.dto = dto;
        }
        
    }
    Map<String, Integer> sequenceNumbersFromNodes;
    Map<String, DTOSeq> DTOSeqPerSender;
    
    int myLastSendSeqNr;
    Map<String, LinkedList<DTOSeq>> holdBackQ;
    Map<Integer, MulticastDTO> mySendSeqNrs;
    CmdMessageHandler mainHandler;

    public BMulticast(Node node, CmdMessageHandler mainHandler){
        super(node);
        sequenceNumbersFromNodes = new HashMap<String, Integer>();
        mySendSeqNrs = new HashMap<Integer, MulticastDTO>();
        myLastSendSeqNr=0;
        this.mainHandler = mainHandler;
        holdBackQ = new HashMap<String, LinkedList<DTOSeq>>();
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
            deliver(dto, dto.ip);
        } else if (receivedSeq < mySeqNr) {
            //reject message because it has been delivered before
        } else if (receivedSeq > mySeqNr) {
                    //messages have been missed. send a request to get them
            //send a request to get the messages again
            MulticastDTO requestMissingIds = new MulticastDTO();
            requestMissingIds.command = MulticastDTO.cmdType.request;
            for (int i = mySeqNr + 1; i < receivedSeq; i++) {
                requestMissingIds.getRequestSeqNums().add(i);
                //network.send(resetreq, multidto.getNodeId(), multidto.getNodeport()); THIS DOES NOT WORK (ANGELO)
            }
            send(dto, new InetSocketAddress(multidto.ip, multidto.port));
        }
    }
    
    public void deliver(DTO data, String sender) {
        //handle the dto the multicast had send
        mainHandler.handleDTO(data);        

        //increase the sequence number 
        int seqNodeNow = sequenceNumbersFromNodes.get(sender);
        seqNodeNow++;
        sequenceNumbersFromNodes.put(sender, seqNodeNow);
        
        DTOSeq dtowithseqnr = holdBackQ.get(sender).peek();
        
        if(dtowithseqnr.sequenceNumber == seqNodeNow+1){
            deliver(dtowithseqnr.dto, sender);
            this.holdBackQ.get(sender).remove();
        }
    }
    
    public void sendMulticast(DTO data){
        myLastSendSeqNr++;
        MulticastDTO multiDTOToSend = new MulticastDTO(
                data, myLastSendSeqNr, MulticastDTO.cmdType.send
        );
        
        network.sendMulticast(multiDTOToSend);
        
        mySendSeqNrs.put(myLastSendSeqNr, multiDTOToSend);
    }
}
