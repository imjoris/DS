package ds.rug.nl.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author angelo
 */
public class VectorClock extends HashMap<Integer, Integer> {

    public Integer get(Integer key) {
        if (!(super.containsKey(key))) {
            super.put(key, 0);
            return 0;
        }
        return super.get(key);
    }

    public Boolean isNext(VectorClock vc, Integer senderKey) {

        Set<Integer> s = this.keySet();
        s.addAll(vc.keySet());

        s.remove(senderKey);
        if (!(this.get(senderKey) + 1 == vc.get(senderKey))) {
            return false;
        }

        Iterator<Integer> sIterator = s.iterator();
        while (sIterator.hasNext()) {
            Integer key = sIterator.next();
            if (!(vc.get(key) > this.get(key))) {
                return false;
            }
        }
        return true;
    }
    
    public void incementValue(Integer id){
        super.put(id, super.get(id)+1);
    }

}
