/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Bart
 */
public class NodeInfo {
    protected final InetAddress ipAddress;
    protected final String name;
    protected final int nodeId;

    public NodeInfo(String ipAddress, String name) throws UnknownHostException {
        this.ipAddress = InetAddress.getByName(ipAddress);
        this.name = name;
        this.nodeId = ipAddress.hashCode();
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
