/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

import ds.rug.nl.main.NodeInfo;

/**
 *
 * @author Bart
 */
public class JoinAnnounceDTO extends DTO {
    public final NodeInfo joinedNode;
    public final NodeInfo parentNode;

    public JoinAnnounceDTO(NodeInfo joinedNode, NodeInfo parentNode) {
        this.joinedNode = joinedNode;
        this.parentNode = parentNode;
    }    
}
