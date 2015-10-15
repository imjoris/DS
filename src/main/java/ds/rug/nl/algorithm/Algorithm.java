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
