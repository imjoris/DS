package ds.rug.nl.network.DTO;

import java.io.Serializable;

/**
 *
 * @author joris
 */
public abstract class DTO implements Serializable{

    public String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
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
    public int port;
    
    public String nodeName;
    public int nodeId;

    public DTO(){}
    
    // NEVER USED ATM
    public DTO(String ip, int port, String nodeName, int nodeId) {
        this.ip = ip;
        this.port = port;
        this.nodeName = nodeName;
        this.nodeId = nodeId;
    }

}
