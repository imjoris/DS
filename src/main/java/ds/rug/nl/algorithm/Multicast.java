/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.network.ReceivedMessage;

/**
 *
 * @author joris
 */
public class Multicast extends Algorithm{

    @Override
    public void handle(ReceivedMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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