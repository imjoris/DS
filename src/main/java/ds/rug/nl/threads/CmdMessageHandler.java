/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.threads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ds.rug.nl.algorithm.Algorithm;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.StreamDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class CmdMessageHandler implements IReceiver{

    Map<Class, Algorithm> mTypeToHandler;
    private final BlockingQueue queue;
    public CmdMessageHandler(){
        mTypeToHandler=new HashMap<Class, Algorithm>();
        queue = new ArrayBlockingQueue(5, true);
    }
    public void registerAlgorithm(Class messagetype, Algorithm algorithm){
        mTypeToHandler.put(messagetype, algorithm);
    }
    
    public void putDTOToHandle(DTO dto){
        try {
            queue.put(dto);
        } catch (InterruptedException ex) {
            Logger.getLogger(CmdMessageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void handleDTO(DTO dto) {
        
        Algorithm algo = mTypeToHandler.get(dto.getClass());
        if(algo != null){
            if(dto instanceof StreamDTO){
                this.toString().getBytes().clone().getClass().toString();
            }else{
                Integer.toString(123);
            }
            algo.handleMsg(dto);
        }
    }
    
    
}
