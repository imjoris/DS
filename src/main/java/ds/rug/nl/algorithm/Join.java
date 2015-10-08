/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.network.DTO.*;

/**
 *
 * @author joris
 */
public class Join implements IAlgorithm {

    public void handle(DTO message) {
        JoinDTO joinMessage = (JoinDTO) message;
        switch (joinMessage.getCommand()){
            case getinfo:
                // send list or tree
                break;
            case list :
                //received list
                //node.list = list;
                break;
            case tree:
                //received tree
//                node.tree = tree;
//                bestnode = getBestNodeFromTree();
//                bestnode.send(new JoinMessageDTO(joinstream));
             break;
            case joinstream:
                //request to get a stream
//                checkIfPossible();
//                network.multiCast(newnode);
//                streamrecievers.add(newnode);
                
            default:
                break;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
