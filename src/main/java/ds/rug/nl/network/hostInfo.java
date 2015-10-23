/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network;

import ds.rug.nl.main.Node;
import ds.rug.nl.threads.IReceiver;

/**
 *
 * @author joris
 */
    public class hostInfo {
        public String hostname;
        public String ip;
        public int port;
        public IReceiver handler;
        
//        public hostInfo(String hostname, String ip, int port, IReceiver handler) {
//            this.hostname = hostname;
//            this.ip = ip;
//            this.port = port;
//            this.handler = handler;
//        }
//        
        public hostInfo(Node node, int port){
            this.hostname = node.getMachineName();
            this.ip = node.getIpAddress();
            this.port = port;
            this.handler = node.getCmdMessageHandler();
        }
    }
