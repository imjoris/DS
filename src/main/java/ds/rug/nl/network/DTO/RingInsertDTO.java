/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

import ds.rug.nl.main.NodeInfo;

/**
 *
 * @author s2063239
 */
public class RingInsertDTO extends DTO {
    public enum CmdType {
        REQUEST,
        ACCEPT,
        ACCEPT_ACK,
    }
    
    public final CmdType cmd;
    public final NodeInfo newNeighbour;

    public RingInsertDTO(CmdType cmd, NodeInfo newNeighbour) {
        this.cmd = cmd;
        this.newNeighbour = newNeighbour;
    }
    
    
    
}
