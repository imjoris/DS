package ds.rug.nl.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author angelo
 */
public class CasualMulti {

    private Map sendMultiMessages; // <ID, Counter>
    private ArrayList<Tuple> queue = new ArrayList<Tuple>(); // <ID, Counter>


    public CasualMulti() {
        sendMultiMessages = new HashMap();
    }
    
    public void rmvFromList(String Id){
        sendMultiMessages.remove(Id);
    }
    
    public void updateList(String Id, int x){
        sendMultiMessages.put(Id, x);
    }

    public Map getList() {
        return sendMultiMessages;
    }
    
    public ArrayList getQueue(){
        return queue;
    }
    
    public void sendSmthg(){
        
    }
    
    public void receiveSmthg(){
        
    }
    
    public void serializeMe(){
        
    }
    
    /**
     * This takes the ID and the number as parameters.
     * IF checks if the new package has larger counter than the previous one and if id does, it places into the queue.
     * ELSE just update the list.
     * @param Id
     * @param x
     */
    public void checkSequence(String Id, int x){
        if((sendMultiMessages.containsKey(Id)) && ((Integer) sendMultiMessages.get(Id) < x-1)){
            queue.add(new Tuple(Id, x));
        } else{
            updateList(Id,x);
        }
    }
    
    private void updateListFromQueue(){ // NOT SURE IF THIS WORKS YET
        for(Tuple t: queue){
            if(sendMultiMessages.containsKey(t.x)){
                if((t.y) == ((Integer) sendMultiMessages.get(t.x))){
                    
                }
            }
        }
    }
    
}
