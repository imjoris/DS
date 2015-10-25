/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

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

    public DiscoveryAlgo(Node node) {
        super(node);
    }
    
    public String getAPeer(){
        this.multicast(new DiscoveryDTO(DiscoveryDTO.CmdType.request));
        try {
            replyLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(DiscoveryAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replyDto.getIp().getHostAddress();

    }
    
    @Override
    public void handle(DTO message) {
        DiscoveryDTO msg = (DiscoveryDTO) message;
        if (msg.cmd == DiscoveryDTO.CmdType.reply){
            replyLatch.countDown();
            replyDto = msg;
        } else {
            reply(new DiscoveryDTO(DiscoveryDTO.CmdType.reply), msg);
        }
    }
    
}
