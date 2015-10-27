/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

import ds.rug.nl.network.Networking;
import org.junit.Test;

/**
 *
 * @author joris
 */
public class GlobalTest {

    @Test
    public void test() throws InterruptedException {
        Source.main(new String[]{"myhost1", "127.0.0.2"});
        Thread.sleep(5000);
        Networking network = new Networking();
        for (int i = 0; i <6 ; i++) {
            App.main(new String[]{"myclient"+(i+3), Networking.nextIpAddress("127.0.0."+(i+2))});
        }
        while (true) {
            Thread.sleep(2000);
        }
    }
}
