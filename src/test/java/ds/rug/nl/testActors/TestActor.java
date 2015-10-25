/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.testActors;

import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.threads.CmdMessageHandler;
import java.net.UnknownHostException;

/**
 *
 * @author angelo
 */
public class TestActor extends Node {

    
    
    public TestActor(NodeInfo nodeInfo) throws UnknownHostException {
        super(nodeInfo);
    }
    
    @Override
    public CmdMessageHandler getCmdMessageHandler() {
        return cmdMessageHandler;
    } 
    
}
