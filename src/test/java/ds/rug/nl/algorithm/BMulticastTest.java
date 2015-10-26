///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ds.rug.nl.algorithm;
//
//import ds.rug.nl.main.NodeInfo;
//import ds.rug.nl.network.DTO.MulticastDTO;
//import ds.rug.nl.network.DTO.TestDTO;
//import ds.rug.nl.testActors.TestActor;
//import java.net.UnknownHostException;
//import java.util.Map;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import ds.rug.nl.TestingVars.TestingVariables;
//
///**
// *
// * @author angelo
// */
//public class BMulticastTest {
//    
//    private TestActor ta;
//    
//    
//    public BMulticastTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() throws UnknownHostException {
//        ta = new TestActor(new NodeInfo("localhost", "Testcase"));
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of handle method, of class BMulticast.
//     */
//    @Test
//    public void testConstructor() {
//        System.out.println("handle");
//        //DTO dto = null;
//        BMulticast bmInstance = new BMulticast(ta, ta.getCmdMessageHandler());
//        //bmInstance.handle(dto);
//
//    }
//
//    /**
//     * Test of deliver method, of class BMulticast.
//     */
//    @Test
//    public void testDeliver() {
//        System.out.println("deliver #1");   
//        //Create the data to be delivered
//        TestDTO data = new TestDTO(TestingVariables.testString1, "angeloLeft");
//        //Create the multicast instance
//        BMulticast bmInstance = new BMulticast(ta, ta.getCmdMessageHandler());
//        //Create the algorithm to test handle the data
//        TestAlgo testsalgo = new TestAlgo(ta);
//        //Assign an algorithm to the message
//        ta.getCmdMessageHandler().registerAlgorithm(TestDTO.class, testsalgo);
//        //deliver the message
//        bmInstance.deliver(data);
//        
//        Assert.assertEquals(testsalgo.getTestingDTOmsg(), TestingVariables.testString1);
//    }
//    
//    
//    @Test
//    public void testDeliver2() {
//        System.out.println("deliver #2");
//        TestDTO data = new TestDTO(TestingVariables.testString1, "angeloLeft");
//        BMulticast bmInstance = new BMulticast(ta, ta.getCmdMessageHandler());
//        TestAlgo testalgo = new TestAlgo(ta);
//        ta.getCmdMessageHandler().registerAlgorithm(TestDTO.class, testalgo);
//        bmInstance.deliver(data);
//        
//        Assert.assertNotEquals(testalgo.getTestingDTOmsg(), TestingVariables.testString2);
//    }
//    
//    @Test
//    public void testSendMylticast(){
//        System.out.println("sendMulticast #1");
//        BMulticast bmInstance = new BMulticast(ta, ta.getCmdMessageHandler());
//        TestAlgo testalgo = new TestAlgo(ta);
//        ta.getCmdMessageHandler().registerAlgorithm(TestDTO.class, testalgo);
//        
//        for(int i = 0; i < 5; i++){
//            TestDTO data = new TestDTO(TestingVariables.testString1, "angeloLeft");
//            bmInstance.sendMulticast(data);
//        }
//        
//        Map tmpSeq = bmInstance.getSendSeq();
//        
//        Assert.assertEquals(tmpSeq.containsKey(5), true);
//        Assert.assertEquals(5, tmpSeq.size());
//    }
//    
//    //test cases
//    //vector clock
//    
//    @Test
//    public void testHandle(){
//        System.out.println("handle #1");
//        MulticastDTO data = new MulticastDTO(new TestDTO(TestingVariables.testString1, "angeloLeft"), 1, MulticastDTO.cmdType.request);
//        BMulticast bmInstance = new BMulticast(ta, ta.getCmdMessageHandler());
//        TestAlgo testalgo = new TestAlgo(ta);
//        ta.getCmdMessageHandler().registerAlgorithm(TestDTO.class, testalgo);
//        bmInstance.handle(data);
//    }
//}
//
////Assert.assertNotNull(ta.getCmdMessageHandler());
