/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import com.google.gson.Gson;
import ds.rug.nl.network.Networking;
import ds.rug.nl.network.ReceivedMessage;

/**
 *
 * @author joris
 */
public abstract class Algorithm {
    protected Networking network;
    
    Gson gson;
    public Algorithm(){
         gson = new Gson();
        this.network = new Networking();
    }
    
    
    public abstract void handle(ReceivedMessage message);
    
}
