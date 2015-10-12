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

        addSmthg(cm);
        printMap(tmp);
        updateSmth(cm);
        printMap(tmp);
    }

    private void addSmthg(CasualMulti cm) {
        cm.addToList("asdf", 111);
        cm.addToList("qwer", 222);
        cm.addToList("zxcv", 333);
        cm.addToList("poiu", 444);
    }

    private void updateSmth(CasualMulti cm) {
        cm.updateList("asdf");
        cm.updateList("qwer");
        cm.updateList("zxcv");
        cm.updateList("poiu");
    }

    private void rmvSmthg() {

    }

    private void printMap(Map tmp) {
        for (Object key : tmp.keySet()) {
            System.out.println(key + " " + tmp.get(key));
        }
    }

}
