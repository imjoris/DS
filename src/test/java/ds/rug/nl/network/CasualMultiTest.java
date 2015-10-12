/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network;

import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author angelo
 */
public class CasualMultiTest extends TestCase {

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

    public void testSomeMethod() {
        System.out.println("Begin testing");
        CasualMulti cm = new CasualMulti();
        Map tmp = cm.getList();

        addSmthgTest(cm);
        printMap(tmp);
        updateSmthTest(cm);
        printMap(tmp);
        rmvSmthgTest(cm);
        printMap(tmp);
    }

    private void addSmthgTest(CasualMulti cm) {
        System.out.println("Testing addToList");
        cm.addToList("asdf", 111);
        cm.addToList("qwer", 222);
        cm.addToList("zxcv", 333);
        cm.addToList("poiu", 444);
    }

    private void updateSmthTest(CasualMulti cm) {
        System.out.println("Testing updateList");
        cm.updateList("asdf");
        cm.updateList("qwer");
        cm.updateList("zxcv");
        cm.updateList("poiu");
    }

    private void rmvSmthgTest(CasualMulti cm) {
        System.out.println("Testing rmvFromList");
        cm.rmvFromList("asdf");
    }

    private void printMap(Map tmp) {
        for (Object key : tmp.keySet()) {
            System.out.println(key + " " + tmp.get(key));
        }
    }
    

}
