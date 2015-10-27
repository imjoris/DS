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
    private final StreamAlgo<T> streamAlgo;
    private final LeaderAlgo leaderAlgo;

    private boolean isFirst;

    public Client(NodeInfo nodeInfo, StreamHandler streamHandler) {
        super(nodeInfo);
        clientData = new CommonClientData(nodeInfo);

        this.isFirst = false;
        System.out.println("Creating client");

        bMulticast = new BMulticast(this, cmdMessageHandler, clientData);
        cmdMessageHandler.registerAlgorithm(MulticastDTO.class, bMulticast);

        coMulticast = new CoMulticast(this, bMulticast, clientData);
        cmdMessageHandler.registerAlgorithm(COmulticastDTO.class, coMulticast);
        
        discoveryAlgo = new DiscoveryAlgo(this, clientData);
        cmdMessageHandler.registerAlgorithm(DiscoveryDTO.class, discoveryAlgo);
        
        leaderAlgo = new LeaderAlgo(this);
        cmdMessageHandler.registerAlgorithm(ElectionDTO.class, leaderAlgo);
        cmdMessageHandler.registerAlgorithm(RingInsertDTO.class, leaderAlgo);
        
        joinAlgo = new JoinAlgo(this, clientData, coMulticast, discoveryAlgo, leaderAlgo);
        joinAlgo.registerDTOs();

        streamAlgo = new StreamAlgo<T>(this, clientData, streamHandler);
        cmdMessageHandler.registerAlgorithm(StreamDTO.class, streamAlgo);
    }

    public void setFirstNode(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public StreamAlgo<T> getStream() {
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
        if (isFirst) {
            clientData.streamTree = clientData.thisNode;
        } else {
            try {
                joinAlgo.joinTree();
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
