/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

import ds.rug.nl.main.StreamHandler;
import ds.rug.nl.main.Client;
import ds.rug.nl.main.NodeInfo;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bart
 */
public class Source {

    public static void main(String[] args) throws InterruptedException {
        Client<Integer> client;
        try {
            StreamHandler<Integer> sHandle = new IntStreamHandler();

            String Ip;
            String hostName;
            if (args.length > 1) {
                Ip = args[1];
            } else {
                Ip = InetAddress.getLocalHost().getHostAddress();
            }
            if (args.length > 2) {
                hostName = args[2];
            } else {
                hostName = InetAddress.getLocalHost().getHostName();
            }
            NodeInfo nodeInfo = new NodeInfo(Ip, hostName);

            client = new Client<Integer>(nodeInfo, sHandle);
            client.setFirstNode(true);
            class keepSendingThread extends Thread {

                Client client;

                public keepSendingThread(Client client) {
                    this.client = client;
                }

                public void run() {
                    client.start();
                    while (true) {
                        client.getStream().sendData(3);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            keepSendingThread sendThread = new keepSendingThread(client);
            sendThread.start();

        } catch (UnknownHostException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("IP adress did not resolve");
            System.exit(1);
        }

    }

}
