/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.*;
import ds.rug.nl.network.hostInfo;
import java.util.List;

/**
 *
 * @author joris
 */
public class Client extends Node {
//    Node mynode = new Node(ip, name);
//    mynode.join();

    public static void main(String[] args) {
        Client client = new Client();
    }

    public Client() {
        System.out.println("Creating client");
        //this dynamic "getnewip" is not working\
        //this.ipAddress=network.getNewIp();
        //this.ipAddress="127.0.0.4";
        //this.network=new Networking("192.168.0.2");

        dnsAlgo = new DNSAlgo(this);
        cmdMessageHandler.registerAlgorithm(DNSDTO.class, dnsAlgo);

        multiAlgo = new BMulticast(cmdMessageHandler);
        cmdMessageHandler.registerAlgorithm(MulticastDTO.class, multiAlgo);
    }

    public void joinabc() {

        //network.send(new JoinDTO(getinfo), Config.dnsip, Config.commandPort);
    }

    @Override
    public void run() {
        hostInfo info = new hostInfo(this, Config.commandPort);
        network.startReceiving(info);
        //network.startReceiveMulticasts(Config.multicastAdres, Config.multicastPort, cmdMessageHandler);

        List<String> ips = dnsAlgo.getDNSIps();
    }
}
