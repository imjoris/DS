package ds.rug.nl.algorithm;

import ds.rug.nl.main.Config;
import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Networking;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public abstract void handleDTO(DTO message);
    
    
    public void handleMsg(DTO dto){
        node.log.logMsg(dto);
        handleDTO(dto);
    }
    
    
    
    public void send(DTO dto, String ip){
        try {
            this.send(dto, InetAddress.getByName(ip), Config.commandPort);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(DTO dto, NodeInfo otherNode){
        this.send(dto, otherNode, Config.commandPort);
    }   
    public void send(DTO dto, NodeInfo otherNode, int port){
        this.send(dto, otherNode.getIpAddress(), port);
    } 
    public void send(DTO dto, InetAddress address, int port){
        setDTONodeInfo(dto);
        network.send(dto, address, port);       
    }
    
    public void reply(DTO dtoToSend, DTO originDTO){
        this.send(dtoToSend, originDTO.getIp(), originDTO.getPort());
    }
    
    public void multicast(DTO dto){
        setDTONodeInfo(dto);
        network.sendMulticast(dto);
    }
    
    protected void setDTONodeInfo(DTO dto){
        dto.setIp(node.getIpAddress());
        dto.setPort(Config.commandPort);
        dto.setNodeName(node.getName());
        dto.setNodeId(node.getNodeId());
    }
}
