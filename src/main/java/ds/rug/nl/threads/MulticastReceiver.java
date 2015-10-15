/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.threads;

import ds.rug.nl.network.Networking;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author joris
 */
public class MulticastReceiver extends Thread{
    Networking network;
    Map<Integer,Integer> sequenceNumbersPerGroup;
    
    public MulticastReceiver(Networking network){
        sequenceNumbersPerGroup = new HashMap<Integer, Integer>();
        this.network = network;
        
    }
    
    public void run(){
//         while (true) {
//            DTO dto = network.receiveMulticasts();
//            MulticastDTO multidto = (MulticastDTO) dto;
//            int nodeId;
//            int mySeqNr;
//            int receivedSeq;
//            
//            receivedSeq = multidto.getSequencenum();
//            
//            if(multidto.getMessage().equals("resend")){
//                // resend the data sent with the basic multicast
//                // with sequence number receivedSeq.
//                // i'm not sure yet where this data should be stored, 
//                // either networking or node.
//            }
//            Integer nodeSeqInteger = sequenceNumbersPerGroup.get(receivedSeq);
//            if (nodeSeqInteger == null){
//                sequenceNumbersPerGroup.put(new Integer(multidto.getNodeId()), new Integer(0));
//                mySeqNr = 0;
//            }else{
//                mySeqNr = nodeSeqInteger.intValue();
//            }
//            
//            mySeqNr++;
//            
//            if(receivedSeq == mySeqNr){
//                //deliver
//            }else if (receivedSeq < mySeqNr){
//                //reject message because it has been delivered before
//            }else if (receivedSeq > mySeqNr){
//                    //messages have been missed. send a request to get them
//                    //send a request to get the messages again
//                    for(int i = mySeqNr+1; i<receivedSeq; i++){
//                       MulticastDTO resetreq = new MulticastDTO();
//                       resetreq.setMessage("resend");
//                       resetreq.setSequencenum(i);
//                       network.send(resetreq, multidto.getNodeip(), multidto.getNodeport());
//                    }
//            }
//                
//            //}
//        }
    }
}
