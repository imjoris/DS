package ds.rug.nl;

import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
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
        System.out.println( "This is a " );
    }
    public Client(){
        System.out.println("Creating client");
        //this.network=new Networking("192.168.0.2");
        handler = new CmdMessageHandler();
        DNSAlgo dnsAlgo = new DNSAlgo();
        handler.registerAlgorithm(DTO.messageType.dns, dnsAlgo);
        network.startReceiving(Config.commandPort, handler);
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
