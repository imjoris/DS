/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

/**
 *
 * @author Bart
 */
public class DiscoveryDTO extends DTO {
    public enum CmdType{
        request,
        reply,
    }
    
    // the IP is in the DTO itself
    public final CmdType cmd;

    public DiscoveryDTO(CmdType cmd) {
        this.cmd = cmd;
    }
}
