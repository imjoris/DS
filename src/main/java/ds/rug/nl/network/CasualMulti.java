package ds.rug.nl.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author angelo
 */
public class CasualMulti {

    private Map<String, Integer> localVector; // <ID, Counter>
    private Map<String, Integer> rcvVector; // <ID, Counter>
    String ID;

    public CasualMulti() {
        localVector = new HashMap();
        rcvVector = new HashMap();
    }

    public void sendSmthg(){
        
    }
    
    public void receiveSmthg(){
        
    }
    
    public void serializeMe(){
        
    }

    public Boolean cmpQ(){
        Set<String> s = localVector.keySet();
        s.addAll(rcvVector.keySet());
        
        s.remove(ID);
        if(!(localVector.get(ID)+1 == rcvVector.get(ID)))
            return false;
 
        
        Iterator<String> sIterator = s.iterator();
        while(sIterator.hasNext()){
            String key = sIterator.next();
            if(!(rcvVector.get(s) > localVector.get(s)))
                return false;

        }
        return true;
    } 
}
