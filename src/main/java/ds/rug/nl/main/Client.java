package ds.rug.nl.main;

import ds.rug.nl.algorithm.DiscoveryAlgo;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.algorithm.CoMulticast;
import ds.rug.nl.algorithm.JoinAlgo;
import ds.rug.nl.algorithm.LeaderAlgo;
import ds.rug.nl.algorithm.StreamAlgo;
import ds.rug.nl.network.DTO.*;
import ds.rug.nl.network.hostInfo;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class Client<T> extends Node {

    // Variables are shared with multiple algorithms
    private final CommonClientData clientData;
    
    private final CoMulticast coMulticast;
    private final DiscoveryAlgo discoveryAlgo;
    private final JoinAlgo joinAlgo;
    private final LeaderAlgo leaderAlgo;
    private final StreamAlgo<T> streamAlgo;

    public Client(NodeInfo nodeInfo, StreamHandler streamHandler) {
        super(nodeInfo);
        clientData = new CommonClientData(nodeInfo);
        
        System.out.println("Creating client");

        bMulticast = new BMulticast(this, cmdMessageHandler, clientData);
        cmdMessageHandler.registerAlgorithm(MulticastDTO.class, bMulticast);
        
        coMulticast = new CoMulticast(this, bMulticast, clientData);
        cmdMessageHandler.registerAlgorithm(COmulticastDTO.class, coMulticast);
        
        discoveryAlgo = new DiscoveryAlgo(this);
        cmdMessageHandler.registerAlgorithm(DiscoveryDTO.class, discoveryAlgo);
        
        leaderAlgo = new LeaderAlgo(this, bMulticast);
        
        
        joinAlgo = new JoinAlgo(this, clientData, coMulticast, discoveryAlgo, leaderAlgo); 
        joinAlgo.registerDTOs();
        
        streamAlgo = new StreamAlgo<T>(this, clientData, streamHandler);
        cmdMessageHandler.registerAlgorithm(StreamDTO.class, streamAlgo);
    }
    
    public StreamAlgo<T> getStream(){
        return this.streamAlgo;
    }

    public void joinabc() {

        //network.send(new JoinDTO(getinfo), Config.dnsip, Config.commandPort);
    }

    @Override
    public void run() {
        hostInfo info = new hostInfo(this, Config.commandPort);
        network.startReceiving(info);
        network.startReceiveMulticasts(info, bMulticast);
        try {
            joinAlgo.joinTree();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
