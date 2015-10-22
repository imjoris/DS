package ds.rug.nl.algorithm;

import ds.rug.nl.network.DTO.*;
import ds.rug.nl.network.ReceivedMessage;

/**
 *
 * @author joris
 */
//public class Join extends Algorithm {
//
//    public void handle(ReceivedMessage message) {
////        JoinDTO joinMessage = (JoinDTO) message;
////        switch (joinMessage.getCommand()){
////            case getinfo:
////                // send list or tree
////                break;
////            case list :
////                //received list
////                //node.list = list;
////                break;
////            case tree:
////                //received tree
//////                node.tree = tree;
//////                bestnode = getBestNodeFromTree();
//////                bestnode.send(new JoinMessageDTO(joinstream));
////             break;
////            case requestjoin:
////                //request to get a stream
//////                checkIfPossible();
//////                network.multiCast(newnode);
//////                streamrecievers.add(newnode);
////                
////            default:
////                break;
////        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    public void startJoining(){
//        JoinDTO dto = new JoinDTO(JoinDTO.cmdType.requestjoin);
//        //this.network.sendCommand(dto);
//    }
//    
//}
