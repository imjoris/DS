/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

import ds.rug.nl.main.NodeInfo;

/**
 *
 * @author angelo
 */
public class ClaimLeadershipDTO extends DTO{
    public final NodeInfo leaderNode;

    public ClaimLeadershipDTO(NodeInfo leaderNode) {
        this.leaderNode = leaderNode;
    }

}
