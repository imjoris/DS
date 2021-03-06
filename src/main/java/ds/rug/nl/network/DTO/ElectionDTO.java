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
public class ElectionDTO extends DTO {
    public enum CmdType {
        ELECTION,
        LEADER,
    }
    
    public final NodeInfo BestNode;
    public final CmdType cmd;

    public ElectionDTO(NodeInfo bestId, CmdType cmd) {
        this.BestNode = bestId;
        this.cmd = cmd;
    }
    
}
