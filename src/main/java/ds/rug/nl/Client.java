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
public class Client extends Node{
//    Node mynode = new Node(ip, name);
//    mynode.join();
    public CmdMessageHandler handler;
    
    public static void main( String[] args )
    {
        Client client = new Client();
    }
    public Client(){
        this.ipAddress=network.getNewIp();
        //this.network=new Networking("192.168.0.2");
        handler = new CmdMessageHandler();
        DNSAlgo dnsAlgo = new DNSAlgo();
        handler.registerAlgorithm(DTO.messageType.dns, dnsAlgo);
        network.startReceiving(this.ipAddress, Config.commandPort, handler);
        dnsAlgo.getDNSIps();
        while(dnsAlgo.hasReceivedIps != true){}
        for(String ip:dnsAlgo.ips){
            System.out.println(dnsAlgo.ips);
        }       
    }
    public void join(){
        
        //network.send(new JoinDTO(getinfo), Config.dnsip, Config.commandPort);
    }
}
