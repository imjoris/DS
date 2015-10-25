package ds.rug.nl.algorithm;

import ds.rug.nl.network.DTO.COmulticastDTO;
import ds.rug.nl.network.DTO.DTO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author angelo
 */
public class CoMulticastTest {
    
    public CoMulticastTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendSmthg method, of class CoMulticast.
     */
    @Ignore @Test
    public void testSendSmthg() {
        System.out.println("sendSmthg");
        DTO data = null;
        CoMulticast instance = null;
        instance.sendSmthg(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receiveSmthg method, of class CoMulticast.
     */
    @Ignore @Test
    public void testReceiveSmthg() {
        System.out.println("receiveSmthg");
        COmulticastDTO msg = null;
        CoMulticast instance = null;
        instance.receiveSmthg(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deliverDTO method, of class CoMulticast.
     */
    @Ignore @Test
    public void testDeliverDTO() {
        System.out.println("deliverDTO");
        DTO data = null;
        Integer sender = 5;
        CoMulticast instance = null;
        instance.deliverDTO(data, sender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handle method, of class CoMulticast.
     */
    @Test
    public void testHandle() {
        System.out.println("handle");
        DTO message = null;
        CoMulticast instance = null;
        instance.handle(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
