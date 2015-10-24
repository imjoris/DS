package ds.rug.nl;

import ds.rug.nl.algorithm.DNSAlgo;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.hostInfo;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void TestDNS() throws InterruptedException, UnknownHostException
    {
        ArrayList<String> dnsIps = new ArrayList<String>();
        dnsIps.add("123.123.123.123");
        
        NodeInfo dnsInfo = new NodeInfo("127.0.0.2", "dns1");
        DNSNode dnsNode = new DNSNode(dnsInfo);
        dnsNode.getDnsAlgo().ips.addAll(dnsIps);
        dnsNode.start();
        
        NodeInfo clientInfo = new NodeInfo("127.0.0.3", "client1");
        Client clientNode = new Client(clientInfo);
        
        hostInfo info = new hostInfo(clientNode, Config.commandPort);
        clientNode.getNetwork().startReceiving(info);
        DNSAlgo dnsAlgo = clientNode.getDnsAlgo();
        List<String> ips = dnsAlgo.getDNSIps();
        
        assertTrue(clientNode.getDnsAlgo().hasReceivedIps);
        assertEquals(dnsIps, ips);
    }
}
