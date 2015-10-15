/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.Config;
import ds.rug.nl.network.DTO.DNSDTO;
import ds.rug.nl.network.ReceivedMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joris
 */
public class DNSAlgo extends Algorithm{
    public List<String> ips;
    public boolean hasReceivedIps;
    public DNSAlgo(){
        hasReceivedIps=false;
        this.ips = new ArrayList<String>();
    }
    
    public void getDNSIps(){
        hasReceivedIps = false;
        DNSDTO dnsDto = new DNSDTO();
        dnsDto.command=DNSDTO.cmdType.request;
        String jsonMessage=gson.toJson(dnsDto);
        network.send(jsonMessage, Config.dnsip, Config.commandPort);
    }

    @Override
    public void handle(ReceivedMessage message) {
                DNSDTO dnsMessage = gson.fromJson(message.data, DNSDTO.class);
        if(dnsMessage.command == DNSDTO.cmdType.request){
            DNSDTO returnDTO = new DNSDTO();
            returnDTO.ips=this.ips;
            returnDTO.command=DNSDTO.cmdType.response;
            
            String jsonString = gson.toJson(returnDTO);
            network.send(jsonString, message.ip, Config.commandPort);
        }else if(dnsMessage.command == DNSDTO.cmdType.response){
            this.ips=dnsMessage.ips;
            hasReceivedIps = true;
        }
    }
}
