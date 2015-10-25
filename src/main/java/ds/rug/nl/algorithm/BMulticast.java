package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.MulticastDTO;
import ds.rug.nl.threads.CmdMessageHandler;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Basic multicast algorithm.
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
    
    Map<Integer, Integer> sequenceNumbersFromNodes;
    Map<String, DTOSeq> DTOSeqPerSender;
    
    int myLastSendSeqNr;
    Map<Integer, LinkedList<DTOSeq>> holdBackQ;
    Map<Integer, MulticastDTO> mySendSeqNrs;
    CmdMessageHandler mainHandler;

    public BMulticast(Node node, CmdMessageHandler mainHandler){
        super(node);
        sequenceNumbersFromNodes = new VectorClock();
        mySendSeqNrs = new HashMap<Integer, MulticastDTO>();
        myLastSendSeqNr=0;
        this.mainHandler = mainHandler;
        holdBackQ = new HashMap<Integer, LinkedList<DTOSeq>>();
    }
    
    @Override
    public void handle(DTO dto) {
        MulticastDTO multidto = (MulticastDTO) dto;
        int receivedSeq;

        if (multidto.command == MulticastDTO.cmdType.request) {
                // resend the data sent with the basic multicast
            // with sequence number receivedSeq.
            // i'm not sure yet where this data should be stored, 
            // either networking or node.
        }

        receivedSeq = multidto.getSequencenum();
        Integer mySeqNr = sequenceNumbersFromNodes.get(multidto.nodeId);

        mySeqNr++;
        if (receivedSeq == mySeqNr) {
            deliver(dto);
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
            send(dto, multidto.ip, multidto.port);
        }
    }
    
    public void deliver(DTO data) {
        
        //handle the dto the multicast had send
        mainHandler.handleDTO(data);
        
        Integer sender = data.nodeId;
        
        //increase the sequence number 
        int seqNodeNow = sequenceNumbersFromNodes.get(sender);
        seqNodeNow++;

        sequenceNumbersFromNodes.put(sender, seqNodeNow);
        
        if(null == holdBackQ.get(sender)){
            return;
        }
        
        DTOSeq dtoWithSeqNumber = holdBackQ.get(sender).peek();
        
        if(dtoWithSeqNumber.sequenceNumber == seqNodeNow+1){
            deliver(dtoWithSeqNumber.dto);
            this.holdBackQ.get(sender).remove();
        }
    }
    
    public void sendMulticast(DTO data){
        myLastSendSeqNr++;
        MulticastDTO multiDTOToSend = new MulticastDTO(
                data, myLastSendSeqNr, MulticastDTO.cmdType.send
        );
        
        this.multicast(multiDTOToSend);
        
        mySendSeqNrs.put(myLastSendSeqNr, multiDTOToSend);
    }
    
    public Map getSendSeq(){
        return mySendSeqNrs;
    }
    
}
