package ds.rug.nl.network.DTO;

/**
 *
 * @author joris
 */
public class DTO {

    public enum messageType {
        dns,
        join,
        election,
        multicast
    }

    public messageType messagetype;
    String nodeName;
    int nodeId;
    String nodeIp;

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }
    
    public messageType getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(messageType messagetype) {
        this.messagetype = messagetype;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
