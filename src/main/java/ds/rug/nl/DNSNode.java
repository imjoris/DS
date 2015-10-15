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
import static ds.rug.nl.network.Networking.nextIpAddress;
import ds.rug.nl.threads.CmdMessageHandler;
import ds.rug.nl.threads.IReceiver;

/**
 *
 * @author joris
 */
public class DNSNode extends Node{
    public static void main( String[] args )
    {
        DNSNode dnsNode = new DNSNode();
    }
    public DNSNode(){
        //request the dns ips
        
        //network.startReceiving(Config.dnsip, Config.commandPort, handler);
        
        network.startReceiving(Config.dnsip, Config.commandPort, this.cmdMessageHandler);
        while(true){}
        //while(dnsAlgo.hasReceivedIps != true){}
        //if(dnsAlgo.hasReceivedIps == true)
        //System.out.println("yay");
    }
}
