package ds.rug.nl.network.DTO;

/**
 *
 * @author joris
 */
public abstract class DTO {

    public enum messageType {
        dns,
        join,
        election
    }

    messageType messagetype;
    String nodeName;
    int nodeId;
    
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
