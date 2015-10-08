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
public abstract class DTO {

    public enum messageType {

        join,
        election
    }
    String nodeip;
    int nodeport;

    public String getNodeip() {
        return nodeip;
    }

    public void setNodeip(String nodeip) {
        this.nodeip = nodeip;
    }

    public int getNodeport() {
        return nodeport;
    }

    public void setNodeport(int nodeport) {
        this.nodeport = nodeport;
    }

    messageType type;
    int nodeId;

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
    String nodeName;

    public messageType getType() {
        return type;
    }

    public void setType(messageType type) {
        this.type = type;
    }
}
