/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.*;
import ds.rug.nl.network.hostInfo;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class DNSNode extends Node {

    public DNSNode(NodeInfo nodeInfo) {
        super(nodeInfo);

        //request the dns ips
        //register the dnsAlgorithm with the cmd message handler
        //this way, all the dns message types get handled by the
        //dnsAlgo class
        dnsAlgo = new DNSAlgo(this);
        cmdMessageHandler.registerAlgorithm(DNSDTO.class, dnsAlgo);

        bMulticast = new BMulticast(this, cmdMessageHandler);
        cmdMessageHandler.registerAlgorithm(MulticastDTO.class, bMulticast);

        //while(dnsAlgo.hasReceivedIps != true){}
        //if(dnsAlgo.hasReceivedIps == true)
        //System.out.println("yay");
    }

    @Override
    public void run() {
        hostInfo info = new hostInfo(this, Config.commandPort);
        network.startReceiving(info);
        this.keepRunning();
        //network.startReceiveMulticasts(Config.multicastAdres, Config.multicastPort, cmdMessageHandler);
    }
}