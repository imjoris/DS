package ds.rug.nl.threads;

import ds.rug.nl.algorithm.Algorithm;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Networking;
import java.util.Map;

/**
 *
 * @author joris
 */
public class CmdReceiver extends Thread{

    Map<DTO.messageType, Algorithm> mTypeToHandler;
    boolean receiving;
    Networking network;

    public CmdReceiver(Networking network){
        this.network = network;
    }
    
    public void run(){
        receiving = true;
        while (receiving){
            //DTO dto = network.receive(Config.commandPort); THIS DOESNT WORK AND I DONT KNOW WHY (ANGELO)
            //process dto by calling an algorithm.
        }
        
    }

}
