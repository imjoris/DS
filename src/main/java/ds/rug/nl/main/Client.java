package ds.rug.nl.main;

import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.algorithm.CoMulticast;
import ds.rug.nl.algorithm.JoinAlgo;
import ds.rug.nl.algorithm.StreamAlgo;
import ds.rug.nl.main.CommonClientData;
import ds.rug.nl.main.Node;
import ds.rug.nl.main.StreamHandler;
import ds.rug.nl.network.DTO.*;
import ds.rug.nl.network.hostInfo;
import ds.rug.nl.tree.TreeNode;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class Client extends Node {

    // Variables are shared with multiple algorithms
    private final CommonClientData clientData;

    public Client(NodeInfo nodeInfo, StreamHandler streamHandler) {
        super(nodeInfo);
        clientData = new CommonClientData();
        
        System.out.println("Creating client");

        dnsAlgo = new DNSAlgo(this);
        cmdMessageHandler.registerAlgorithm(DNSDTO.class, dnsAlgo);

        bMulticast = new BMulticast(this, cmdMessageHandler);
        cmdMessageHandler.registerAlgorithm(MulticastDTO.class, bMulticast);
        
        CoMulticast coMulticast = new CoMulticast(this, bMulticast);
        cmdMessageHandler.registerAlgorithm(COmulticastDTO.class, coMulticast);        
        
        JoinAlgo joinAlgo = new JoinAlgo(this, clientData, coMulticast); 
        joinAlgo.registerDTOs();
        
        StreamAlgo streamAlgo = new StreamAlgo(this, clientData, streamHandler);
        cmdMessageHandler.registerAlgorithm(StreamDTO.class, streamAlgo);
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
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
