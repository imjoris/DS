/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

/**
 *
 * @author joris
 */
public class DTO {

    public enum messageType {
        dns,
        join,
        election
    }

    public messageType messagetype;
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
