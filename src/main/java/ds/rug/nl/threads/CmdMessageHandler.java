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
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joris
 */
public class CmdMessageHandler implements IReceiver{

    Map<Class, Algorithm> mTypeToHandler;
    Gson gson;
    public CmdMessageHandler(){
        gson = new GsonBuilder().create();
        mTypeToHandler=new HashMap<Class, Algorithm>();
    }
    public void registerAlgorithm(Class messagetype, Algorithm algorithm){
        mTypeToHandler.put(messagetype, algorithm);
    }
//    public void run() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void handleDTO(DTO dto) {
        
        Algorithm algo = mTypeToHandler.get(dto.getClass());
        if(algo != null){
            algo.handle(dto);
        }
    }
}
