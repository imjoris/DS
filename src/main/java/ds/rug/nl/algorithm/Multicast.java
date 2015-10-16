package ds.rug.nl.algorithm;

import com.google.gson.Gson;
import ds.rug.nl.network.DTO.MulticastDTO;
import ds.rug.nl.network.ReceivedMessage;
import java.util.Map;

/**
 *
 * @author joris
 */
public class Multicast extends Algorithm {

    Map<Integer, Integer> sequenceNumbersPerNode;
    Gson gson;

    @Override
    public void handle(ReceivedMessage message) {
        MulticastDTO multidto = gson.fromJson(message.data, MulticastDTO.class);
        int nodeId;
        int receivedSeq;

        if (multidto.command == MulticastDTO.cmdType.request) {
                // resend the data sent with the basic multicast
            // with sequence number receivedSeq.
            // i'm not sure yet where this data should be stored, 
            // either networking or node.
        }

        receivedSeq = multidto.getSequencenum();
        Integer mySeqNr = sequenceNumbersPerNode.get(receivedSeq);

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
}
