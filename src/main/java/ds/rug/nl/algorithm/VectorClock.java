package ds.rug.nl.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author angelo
 */
public class VectorClock extends HashMap<Integer, Integer> {

    @Override
    public Integer get(Object key) {
        if (!(super.containsKey(key))) {
            super.put((Integer) key, 0);
            return 0;
        }
        return super.get(key);
    }

    public Boolean isNext(VectorClock vc, Integer senderKey) {

        Set<Integer> s = new HashSet<Integer>(this.keySet());
        s.addAll(new HashSet<Integer>(vc.keySet()));

        s.remove(senderKey);
        if (!(this.get(senderKey) + 1 == vc.get(senderKey))) {
            return false;
        }
        
        System.out.println(senderKey.toString() + " got to poit 1");
        Iterator<Integer> sIterator = s.iterator();
        while (sIterator.hasNext()) {
            Integer key = sIterator.next();
            if (!(vc.get(key) >= this.get(key))) {
                System.out.println(senderKey.toString() +" point 2: did not accept the vector clock");
                return false;
            }
        }
        return true;
    }
    
    public void incrementValue(Integer id){
        super.put(id, get(id)+1);
    }
}
