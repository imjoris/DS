package ds.rug.nl.algorithm;

import ds.rug.nl.Config;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DNSDTO;
import ds.rug.nl.network.ReceivedMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class DNSAlgo extends Algorithm {

    CountDownLatch ipsLatch;
    public List<String> ips;
    public boolean hasReceivedIps;
    public Node node;

    public DNSAlgo(Node node) {
        this.node = node;
        hasReceivedIps = false;
        ips = new ArrayList<String>();
    }

    public List<String> getDNSIps() {
        return this.getDNSIps(Config.dnsip, Config.commandPort);
    }

    public List<String> getDNSIps(String ip, int port) {
        ipsLatch = new CountDownLatch(1);

        hasReceivedIps = false;
        DNSDTO dnsDto = new DNSDTO();
        dnsDto.command = DNSDTO.cmdType.request;
        dnsDto.setNodeIp(this.node.getIpAddress());
        String jsonMessage = gson.toJson(dnsDto);
        network.send(jsonMessage, ip, port);
        
        try {
            ipsLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(DNSAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ips;
    }
    
    public void setDNSIps(){
        
    }

    @Override
    public void handle(ReceivedMessage message) {
        DNSDTO dnsMessage = gson.fromJson(message.data, DNSDTO.class);
        if (dnsMessage.command == DNSDTO.cmdType.request) {
            DNSDTO returnDTO = new DNSDTO();
            returnDTO.ips = this.ips;
            returnDTO.command = DNSDTO.cmdType.response;
            returnDTO.setNodeIp(this.node.getIpAddress());
            String jsonString = gson.toJson(returnDTO);
            network.send(jsonString, dnsMessage.getNodeIp(), Config.commandPort);
        } else if (dnsMessage.command == DNSDTO.cmdType.response) {
            this.ips = dnsMessage.ips;
            
            ipsLatch.countDown();
            hasReceivedIps = true;
        }
    }
}
