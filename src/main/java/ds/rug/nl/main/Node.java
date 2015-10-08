/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.Config;
import ds.rug.nl.network.DTO.JoinDTO;
import static ds.rug.nl.network.DTO.JoinDTO.cmdType.getinfo;
import ds.rug.nl.network.Networking;

/**
 *
 * @author joris
 */
public class Node {
    private String ipAddress = "localhost";
    private String machineName;
    private String id;
    
    Networking network;
    
    public void join(){
        network.send(new JoinDTO(getinfo), Config.dnsip, Config.commandPort);
    }
    
        public void execute() {
        //To change body of generated methods, choose Tools | Templates.
    }
}
