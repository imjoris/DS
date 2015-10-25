package ds.rug.nl.main;

import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.algorithm.CoMulticast;
import ds.rug.nl.algorithm.JoinAlgo;
import ds.rug.nl.algorithm.StreamAlgo;
import ds.rug.nl.network.DTO.*;
import ds.rug.nl.network.hostInfo;
import java.net.UnknownHostException;
import java.util.List;
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
    private final JoinAlgo joinAlgo;
    private final StreamAlgo<T> streamAlgo;

    public Client(NodeInfo nodeInfo, StreamHandler streamHandler) {
        super(nodeInfo);
        clientData = new CommonClientData();
        
        System.out.println("Creating client");

        dnsAlgo = new DNSAlgo(this);
        cmdMessageHandler.registerAlgorithm(DNSDTO.class, dnsAlgo);

        bMulticast = new BMulticast(this, cmdMessageHandler);
        cmdMessageHandler.registerAlgorithm(MulticastDTO.class, bMulticast);
        
        coMulticast = new CoMulticast(this, bMulticast);
        cmdMessageHandler.registerAlgorithm(COmulticastDTO.class, coMulticast);        
        
        joinAlgo = new JoinAlgo(this, clientData, coMulticast); 
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
        try {
            List<String> ips = dnsAlgo.getDNSIps();
            joinAlgo.joinTree();
            // hartbeat stuff?
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
