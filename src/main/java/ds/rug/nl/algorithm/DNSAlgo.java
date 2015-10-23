package ds.rug.nl.algorithm;

import ds.rug.nl.Config;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DNSDTO;
import ds.rug.nl.network.DTO.DTO;
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
        dnsDto.setIp(this.node.getIpAddress());
        network.send(dnsDto, ip, port);
        
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
    public void handle(DTO dto) {
        DNSDTO dntDto = (DNSDTO) dto;
        if (dntDto.command == DNSDTO.cmdType.request) {
            DNSDTO returnDTO = new DNSDTO();
            returnDTO.ips = this.ips;
            returnDTO.command = DNSDTO.cmdType.response;
            returnDTO.setIp(this.node.getIpAddress());
            network.send(returnDTO, dto.getIp(), Config.commandPort);
        } else if (dntDto.command == DNSDTO.cmdType.response) {
            this.ips = dntDto.ips;
            
            ipsLatch.countDown();
            hasReceivedIps = true;
        }
    }
}
