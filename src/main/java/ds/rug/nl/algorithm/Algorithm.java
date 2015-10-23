package ds.rug.nl.algorithm;

import ds.rug.nl.Config;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Networking;
import java.net.InetSocketAddress;

/**
 *
 * @author joris
 */
public abstract class Algorithm {

    protected Networking network;
    protected Node node;
    
    public Algorithm(Node node) {
        this.node = node;
        this.network = new Networking();
    }

    public abstract void handle(DTO message);

    public void send(DTO dto, InetSocketAddress address){
        dto.ip = node.getIpAddress();
        dto.port = Config.commandPort;
        dto.nodeName = node.getName();
        dto.nodeId = node.getNodeId();
        network.send(dto, address);
    }
}
