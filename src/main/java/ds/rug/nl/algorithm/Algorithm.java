package ds.rug.nl.algorithm;

import ds.rug.nl.Config;
import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Networking;
import java.net.InetAddress;

/**
 *
 * @author joris
 */
public abstract class Algorithm {

    private Networking network;
    protected Node node;
    
    public Algorithm(Node node) {
        this.node = node;
        this.network = new Networking();
    }

    public abstract void handle(DTO message);
    
    public void send(DTO dto, NodeInfo otherNode){
        this.send(dto, otherNode, Config.commandPort);
    }   
    public void send(DTO dto, NodeInfo otherNode, int port){
        this.send(dto, otherNode.getIpAddress(), port);
    } 
    public void send(DTO dto, InetAddress address, int port){
        dto.ip = node.getIpAddress();
        dto.port = Config.commandPort;
        dto.nodeName = node.getName();
        dto.nodeId = node.getNodeId();
        network.send(dto, address, port);       
    }
    
    public void reply(DTO dtoToSend, DTO originDTO){
        this.send(dtoToSend, originDTO.getIp(), originDTO.getPort());
    }
    
    public void multicast(DTO dto){
        network.sendMulticast(dto);
    }
}
