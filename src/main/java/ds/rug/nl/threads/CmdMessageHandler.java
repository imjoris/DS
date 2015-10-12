/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.threads;

import ds.rug.nl.Config;
import ds.rug.nl.algorithm.Algorithm;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Networking;
import ds.rug.nl.network.ReceivedMessage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joris
 */
public class CmdMessageHandler implements IReceiver{

    Map<DTO.messageType, Algorithm> mTypeToHandler;
    
    public CmdMessageHandler(){
        mTypeToHandler=new HashMap<DTO.messageType, Algorithm>();
    }
    public void registerAlgorithm(DTO.messageType messagetype, Algorithm algorithm){
        mTypeToHandler.put(messagetype, algorithm);
    }
//    public void run() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public void handleMessage(ReceivedMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
