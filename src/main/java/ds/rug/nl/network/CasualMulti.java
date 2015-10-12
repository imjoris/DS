/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author angelo
 */
public class CasualMulti {

    private Map sendMultiMessages; // <ID, Counter>    

    public CasualMulti() {
        sendMultiMessages = new HashMap();
    }

    public void addToList(String Id, int x) {
        sendMultiMessages.put(Id, x);
    }

    public void updateList(String Id) {
        if (sendMultiMessages.containsKey(Id)) {
            sendMultiMessages.put(Id, (Integer) sendMultiMessages.get(Id) + 1);
        } else {
            addToList(Id, 1);
        }
    }
    
    public void updateList(String Id, int x){
        if (sendMultiMessages.containsKey(Id)){
            sendMultiMessages.put(Id, x);
        }
    }

    public Map getList() {
        return sendMultiMessages;
    }
    
    public void sendSmthg(){
        
    }
    
    public void receiveSmthg(){
        
    }
}
