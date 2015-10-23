package ds.rug.nl.network.DTO;

import java.io.Serializable;

/**
 *
 * @author joris
 */
public abstract class DTO implements Serializable{

    public enum messageType {
        dns,
        join,
        election,
        multicast
    }
    
    public messageType messagetype;
    public String ip;

    public messageType getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(messageType messagetype) {
        this.messagetype = messagetype;
    }

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

    public DTO(){
        
    
    }
    
    
    public DTO(messageType messagetype, String ip, int port, String nodeName, int nodeId) {
        this.messagetype = messagetype;
        this.ip = ip;
        this.port = port;
        this.nodeName = nodeName;
        this.nodeId = nodeId;
    }

}
