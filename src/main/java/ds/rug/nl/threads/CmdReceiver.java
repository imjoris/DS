package ds.rug.nl.threads;

import ds.rug.nl.Config;
import ds.rug.nl.algorithm.IAlgorithm;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Networking;
import java.util.Map;

/**
 *
 * @author joris
 */
public class CmdReceiver extends Thread{

    Map<DTO.messageType, IAlgorithm> mTypeToHandler;
    boolean receiving;
    Networking network;

    public CmdReceiver(Networking network){
        this.network = network;
    }
    
    public void run(){
        receiving = true;
        while (receiving){
            DTO dto = network.receive(Config.commandPort);
            //process dto by calling an algorithm.
        }
        
    }

}
