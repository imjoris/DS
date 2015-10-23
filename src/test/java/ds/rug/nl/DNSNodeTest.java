package ds.rug.nl;

import ds.rug.nl.network.hostInfo;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class DNSNodeTest 
{
//    public DNSNodeTest()
//    {
//    }

    @Test
    public void TestDNS() throws InterruptedException
    {
        ArrayList<String> dnsIps = new ArrayList<String>();
        dnsIps.add("123.123.123.123");
        
        DNSNode dnsNode = new DNSNode();
        dnsNode.setMachineName("dns1");
        dnsNode.getDnsAlgo().ips.addAll(dnsIps);
        dnsNode.start();
        
        Client clientNode = new Client();
        clientNode.setMachineName("client1");
        clientNode.setIpAddress("127.0.0.3");
        
        hostInfo info = new hostInfo(clientNode, Config.commandPort);
        clientNode.getNetwork().startReceiving(info);
        List<String> ips = clientNode.getDnsAlgo().getDNSIps();
        
        assertTrue(clientNode.getDnsAlgo().hasReceivedIps);
        assertEquals(dnsIps, ips);
    }
}
