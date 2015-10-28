package ds.rug.nl.algorithm;

import ds.rug.nl.main.CommonClientData;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.MulticastDTO;
import ds.rug.nl.threads.CmdMessageHandler;
import ds.rug.nl.threads.IReceiver;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic multicast algorithm.
 *
 * @author joris
 */
public class BMulticast extends Algorithm implements IReceiver {

    Map<String, DTOSeq> DTOSeqPerSender;

    int myLastSendSeqNr;
    Map<Integer, LinkedList<DTOSeq>> holdBackQ;
    Map<Integer, MulticastDTO> mySendSeqNrs;
    CmdMessageHandler mainHandler;

    private final CommonClientData clientData;

    private final BlockingQueue sharedQueue;

    public BMulticast(Node node, CmdMessageHandler mainHandler, CommonClientData clientData) {
        super(node);
        mySendSeqNrs = new HashMap<Integer, MulticastDTO>();
        myLastSendSeqNr = 0;
        this.mainHandler = mainHandler;
        holdBackQ = new HashMap<Integer, LinkedList<DTOSeq>>();
        this.clientData = clientData;
        sharedQueue = new LinkedBlockingQueue();
        this.processQueue();
    }
    
    private void processQueue(){
        class mythread extends Thread{
            private final BlockingQueue sharedQueue;
            
          
            public mythread(BlockingQueue sharedBlockingQueue){
                this.sharedQueue = sharedBlockingQueue;
            }
            
            @Override
            public void run(){
                while(true){
                    try {
                        MulticastDTO dto = (MulticastDTO) sharedQueue.take();
                        processDTO(dto);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BMulticast.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        Thread t = new mythread(this.sharedQueue);
        t.start();
    }
    
    public void processDTO(MulticastDTO multidto){
            int receivedSeq;
            
            if (multidto.command != null && multidto.command == MulticastDTO.cmdType.request) {
                MulticastDTO mdto = new MulticastDTO();
                LinkedList<DTOSeq> list = new LinkedList<DTOSeq>();
                
                for (Integer seqNum : mdto.requestSeqNums) {
                    DTOSeq responseDTOseq = new DTOSeq(seqNum, mySendSeqNrs.get(seqNum));
                    list.add(responseDTOseq);
                }
                mdto.responseDTOseq = list;
                reply(mdto, multidto);
            }
            
            receivedSeq = multidto.getSequencenum();
            Integer mySeqNr = clientData.bVector.get(multidto.getNodeId());
            
            mySeqNr++;
            if (receivedSeq == mySeqNr) {
                deliver(multidto.dto);
            } else if (receivedSeq < mySeqNr) {
                //reject message because it has been delivered before
            } else if (receivedSeq > mySeqNr) {
                System.out.println("Requesting missing");
                //messages have been missed. send a request to get them
                //send a request to get the messages again
                MulticastDTO requestMissingIds = new MulticastDTO();
                requestMissingIds.command = MulticastDTO.cmdType.request;
                for (int i = mySeqNr + 1; i < receivedSeq; i++) {
                    requestMissingIds.getRequestSeqNums().add(i);
                }
                send(requestMissingIds, multidto.getIp(), multidto.getPort());
            }
    }

    public void putDTO(DTO dto) {
        try {
            sharedQueue.put(dto);

        } catch (InterruptedException ex) {
            Logger.getLogger(CoMulticast.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handleDTO(DTO dto) {
        try {
            sharedQueue.put(dto);
        } catch (InterruptedException ex) {
            Logger.getLogger(BMulticast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deliver(DTO data) {

        //handle the dto the multicast had send
        mainHandler.handleDTO(data);

        Integer sender = data.getNodeId();

        //increase the sequence number 
        int seqNodeNow = clientData.bVector.get(sender);
        seqNodeNow++;

        clientData.bVector.put(sender, seqNodeNow);

        if (null == holdBackQ.get(sender)) {
            return;
        }

        DTOSeq dtoWithSeqNumber = holdBackQ.get(sender).peek();

        if (dtoWithSeqNumber.sequenceNumber == seqNodeNow + 1) {
            deliver(dtoWithSeqNumber.dto);
            this.holdBackQ.get(sender).remove();
        }
    }

    public void sendMulticast(DTO data) {
        myLastSendSeqNr++;
        MulticastDTO multiDTOToSend = new MulticastDTO(
                data, myLastSendSeqNr, MulticastDTO.cmdType.send
        );

        this.multicast(multiDTOToSend);

        mySendSeqNrs.put(myLastSendSeqNr, multiDTOToSend);
    }

    public Map getSendSeq() {
        return mySendSeqNrs;
    }
}
