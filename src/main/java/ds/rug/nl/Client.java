package ds.rug.nl;

import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.algorithm.JoinAlgo;
import ds.rug.nl.main.Node;
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

    TreeNode<NodeInfo> streamTree;
    TreeNode<NodeInfo> thisNode;

    public Client(NodeInfo nodeInfo) {
        super(nodeInfo);
        
        System.out.println("Creating client");

        dnsAlgo = new DNSAlgo(this);
        cmdMessageHandler.registerAlgorithm(DNSDTO.class, dnsAlgo);

        multiAlgo = new BMulticast(this, cmdMessageHandler);
        cmdMessageHandler.registerAlgorithm(MulticastDTO.class, multiAlgo);
        
        JoinAlgo joinAlgo = new JoinAlgo(this, streamTree, thisNode); 
        cmdMessageHandler.registerAlgorithm(JoinRequestDTO.class , joinAlgo);
        cmdMessageHandler.registerAlgorithm(JoinResponseDTO.class, joinAlgo);
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
