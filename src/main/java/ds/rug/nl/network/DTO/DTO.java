package ds.rug.nl.network.DTO;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author joris
 */
public abstract class DTO implements Serializable{

    private InetAddress ip;
    private String nodeName;
    private int nodeId;
    private int port;
    
    public DTO(){}

    public InetAddress getIp() {
        return ip;
    }    
    
    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }   

}
