package ds.rug.nl.algorithm;

import com.google.gson.Gson;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.MyGson;
import ds.rug.nl.network.Networking;
import java.net.InetSocketAddress;

/**
 *
 * @author joris
 */
public abstract class Algorithm {

    protected Networking network;

    Gson gson;

    public Algorithm() {
        gson = new MyGson().getGson();

        this.network = new Networking();
    }

    public abstract void handle(DTO message);
    
    public void send(DTO dto, InetSocketAddress address){
        
    }

}
