package ds.rug.nl.algorithm;

import ds.rug.nl.Config;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DNSDTO;
import ds.rug.nl.network.DTO.DTO;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    public DNSAlgo(Node node) {
        super(node);
        hasReceivedIps = false;
        ips = new ArrayList<String>();
    }

    public List<String> getDNSIps() throws UnknownHostException {
        InetAddress dnsIp = InetAddress.getByName(Config.dnsip);
        return this.getDNSIps(dnsIp, Config.commandPort);
    }

    public List<String> getDNSIps(InetAddress ip, int port) {
        ipsLatch = new CountDownLatch(1);

        hasReceivedIps = false;
        DNSDTO dnsDto = new DNSDTO();
        dnsDto.command = DNSDTO.cmdType.request;
        dnsDto.setIp(node.getIpAddress());
        send(dnsDto, ip, port);
        
        try {
            ipsLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(DNSAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ips;
    }

    @Override
    public void handle(DTO dto) {
        DNSDTO dntDto = (DNSDTO) dto;
        if (dntDto.command == DNSDTO.cmdType.request) {
            DNSDTO returnDTO = new DNSDTO();
            returnDTO.ips = this.ips;
            returnDTO.command = DNSDTO.cmdType.response;
            returnDTO.setIp(this.node.getIpAddress());
            
            send(returnDTO, dto.getIp(), Config.commandPort);
        } else if (dntDto.command == DNSDTO.cmdType.response) {
            this.ips = dntDto.ips;
            
            ipsLatch.countDown();
            hasReceivedIps = true;
        }
    }
}
