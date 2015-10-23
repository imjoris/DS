/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network;

import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author angelo
 */
public class CasualMultiTest extends TestCase {
    private ArrayList<Tuple> l = new ArrayList<Tuple>();
    
    public CasualMultiTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

//    public void testSomeMethod() {
//        System.out.println("Begin testing");
//        CasualMulti cm = new CasualMulti();
//        Map tmp = cm.getList();
//        
//        
//
//        addSmthgTest(cm);
//        printMap(tmp);
//        updateSmthTest(cm);
//        printMap(tmp);
//        rmvSmthgTest(cm);
//        printMap(tmp);
//        l = cm.getQueue();
//        printQ(l);
//    }
//
//    private void addSmthgTest(CasualMulti cm) {
//        System.out.println("Testing addToList");
//        cm.checkSequence("asdf", 998);
//        cm.checkSequence("qwer", 222);
//        cm.checkSequence("zxcv", 333);
//        cm.checkSequence("poiu", 444);
//    }
//
//    private void updateSmthTest(CasualMulti cm) {
//        System.out.println("Testing updateList");
//        cm.checkSequence("asdf", 999);
//        cm.checkSequence("qwer", 888);
//        cm.checkSequence("zxcv", 777);
//        cm.checkSequence("poiu", 666);
//    }
//
//    private void rmvSmthgTest(CasualMulti cm) {
//        System.out.println("Testing rmvFromList");
//        //cm.rmvFromList("asdf");
//    }
//
//    private void printMap(Map tmp) {
//        System.out.println("Printing map");
//        for (Object key : tmp.keySet()) {
//            System.out.println(key + " " + tmp.get(key));
//        }
//    }
//    
//    private void printQ(ArrayList<Tuple> l){
//        System.out.println("Printing queue");
//        for(Tuple t: l){
//            System.out.println(t.x + " - " + t.y);
//        }
//    }
//    

}
