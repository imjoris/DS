package ds.rug.nl.algorithm;

import ds.rug.nl.network.ReceivedMessage;

/**
 *
 * @author joris
 */
public class Multicast extends Algorithm{

    @Override
    public void handle(ReceivedMessage message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
//sendMultiMessages = new HashMap<Integer, MulticastDTO>();
//    @Override
//    void handle(DTO message) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    void send(){
//        sendMultiMessages.put(dto.getSequencenum(), dto);
//    }
}