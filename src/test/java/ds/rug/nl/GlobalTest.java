/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;
import ds.rug.nl.main.Config;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.MulticastDTO;
import ds.rug.nl.network.Networking;
import ds.rug.nl.network.hostInfo;
import ds.rug.nl.threads.IReceiver;
import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 *
 * @author joris
 */
public class GlobalTest {

    @Test
    public void test() throws InterruptedException{
        Source.main(new String[] {"myhost1", "127.0.0.2"});
        App.main(new String[] {"myhost1", "127.0.0.3"});
        while(true){}
    }
}
