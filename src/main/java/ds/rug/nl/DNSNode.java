/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Networking;
import ds.rug.nl.threads.CmdMessageHandler;

/**
 *
 * @author joris
 */
public class DNSNode extends Node{
    public CmdMessageHandler handler;
    public static void main( String[] args )
    {
        DNSNode dnsNode = new DNSNode();
    }
    public DNSNode(){
        handler = new CmdMessageHandler();
        DNSAlgo dnsAlgo = new DNSAlgo();
        handler.registerAlgorithm(DTO.messageType.dns, dnsAlgo);
        network.startReceiving(Config.commandPort, handler);
    }
}
