package ds.rug.nl.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author angelo
 */
public class VectorClock extends HashMap<String, Integer> {

    @Override
    public Integer get(Object key) {
        if (!(super.containsKey(key))) {
            super.put((String) key, 0);
            return 0;
        }
        return super.get(key);
    }

    public Boolean isNext(VectorClock vc, String senderKey) {

        Set<String> s = this.keySet();
        s.addAll(vc.keySet());

        s.remove(senderKey);
        if (!(this.get(senderKey) + 1 == vc.get(senderKey))) {
            return false;
        }

        Iterator<String> sIterator = s.iterator();
        while (sIterator.hasNext()) {
            String key = sIterator.next();
            if (!(vc.get(key) > this.get(key))) {
                return false;
            }
        }
        return true;
    }
    
    public void incementValue(String id){
        super.put(id, super.get(id)+1);
    }

}
