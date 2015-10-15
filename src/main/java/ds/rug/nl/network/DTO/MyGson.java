/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/**
 *
 * @author joris
 */
public class MyGson {
    final RuntimeTypeAdapterFactory<DTO> typeFactory = RuntimeTypeAdapterFactory
            .of(DTO.class)
            .registerSubtype(DNSDTO.class);
            
    
    static Gson gson;
    
    public MyGson(){
        gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
    }
    
    public Gson getGson(){
        return gson;
    }
    
}