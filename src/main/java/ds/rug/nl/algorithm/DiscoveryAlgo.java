/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.main.CommonClientData;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.DiscoveryDTO;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bart
 */
public class DiscoveryAlgo extends Algorithm {

    private CountDownLatch replyLatch;
    private DiscoveryDTO replyDto;
    CommonClientData clientData;

    public DiscoveryAlgo(Node node, CommonClientData clientData) {
        super(node);
        this.clientData = clientData;
    }

    public String getAPeer() {
        replyLatch = new CountDownLatch(1);
        this.multicast(new DiscoveryDTO(DiscoveryDTO.CmdType.request));
        try {
            replyLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(DiscoveryAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replyDto.getIp().getHostAddress();

    }

    @Override
    public void handleDTO(DTO message) {
        DiscoveryDTO msg = (DiscoveryDTO) message;
        if (msg.cmd == DiscoveryDTO.CmdType.reply) {
            replyDto = msg;
            replyLatch.countDown();
        } else {
            if (clientData.streamTree == null 
                    || message.getIp() == node.getIpAddress()){
                return;
            }
            reply(new DiscoveryDTO(DiscoveryDTO.CmdType.reply), msg);
        }
    }
}