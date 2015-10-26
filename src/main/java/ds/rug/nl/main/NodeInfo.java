/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.network.DTO.DTO;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Bart
 */
public class NodeInfo implements java.io.Serializable {

    protected final InetAddress ipAddress;
    protected final String name;
    protected final int nodeId;

    public NodeInfo(String ipAddress, String name) throws UnknownHostException {
        this.ipAddress = InetAddress.getByName(ipAddress);
        this.name = name;
        this.nodeId = ipAddress.hashCode();
    }

    public NodeInfo(InetAddress ipAddress, String name) throws UnknownHostException {
        this.ipAddress = ipAddress;
        this.name = name;
        this.nodeId = ipAddress.hashCode();
    }

    public NodeInfo(Node node) {
        this.ipAddress = node.getIpAddress();
        this.name = node.getMachineName();
        this.nodeId = node.getNodeId();
    }
    
    public NodeInfo(DTO msg){
        this.ipAddress = msg.ip;
        this.name = msg.nodeName;
        this.nodeId = msg.nodeId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.ipAddress != null ? this.ipAddress.hashCode() : 0);
        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 41 * hash + this.nodeId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodeInfo other = (NodeInfo) obj;
        if (this.ipAddress != other.ipAddress && (this.ipAddress == null || !this.ipAddress.equals(other.ipAddress))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.nodeId != other.nodeId) {
            return false;
        }
        return true;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public String getName() {
        return name;
    }

    public int getNodeId() {
        return nodeId;
    }

}
