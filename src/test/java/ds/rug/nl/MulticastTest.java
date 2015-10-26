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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joris
 */
public class MulticastTest {
    @Test
    public void TestSendReceive() throws InterruptedException, UnknownHostException
    {   
        class testReceiver implements IReceiver{
            boolean hasReceived;
            DTO dtoreceived;
            
            public testReceiver(){
                hasReceived = false;
            }
            
            @Override
            public void handleDTO(DTO dto) {
                hasReceived = true;
                dtoreceived = dto;
            }
            
        }
        //public void startReceiveMulticasts(hostInfo hostinfo, IReceiver handler)
        Networking network = new Networking();
        
        testReceiver receiver = new testReceiver();
        testReceiver receiver2 = new testReceiver();
        
        hostInfo info = new hostInfo("myhostname", InetAddress.getByName("127.0.0.2"), Config.multicastPort, receiver);
        hostInfo info2 = new hostInfo("myhostname2", InetAddress.getByName("127.0.0.3"), Config.multicastPort, receiver2);
        
        network.startReceiveMulticasts(info, receiver);
        network.startReceiveMulticasts(info2, receiver2);
        
        MulticastDTO datatosend = new MulticastDTO();
        datatosend.sequencenum = 123;
        
        network.sendMulticast(datatosend);
        
        Thread.sleep(1000);
        
        assertEquals(receiver.hasReceived, true);
        assertEquals(receiver2.hasReceived, true);
        
        assertEquals(((MulticastDTO)receiver.dtoreceived).sequencenum, 123);
        assertEquals(((MulticastDTO)receiver2.dtoreceived).sequencenum, 123);


    }
    
    public void Test(){
        
    }

}
