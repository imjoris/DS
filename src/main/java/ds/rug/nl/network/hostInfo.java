package ds.rug.nl.network;

import ds.rug.nl.main.Node;
import ds.rug.nl.threads.IReceiver;
import java.net.InetAddress;

/**
 *
 * @author joris
 */
    public class hostInfo {
        public String hostname;
        public InetAddress ip;
        public int port;
        public IReceiver handler;
        
        public hostInfo(Node node, int port){
            this.hostname = node.getMachineName();
            this.ip = node.getIpAddress();
            this.port = port;
            this.handler = node.getCmdMessageHandler();
        }
    }
