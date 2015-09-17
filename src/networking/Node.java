/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

/**
 *
 * @author joris-main
 */
public class Node {
    public enum MessageType {
        GroupManagement 
    }
    
        
    public void receiveMessage(){
        //Get message and check the MessageType
        //Then dispatch it to the corresponding algorithm?
        
//        //MessageType mType = MessageType.GroupManagement;
//        switch(mType){
//            case GroupManagement:
//                  IAlgorithm algorithm = new GroupAlgorithm();
//                    algorithm.handle(message);
//                break;
//                
//        }
    }
}
