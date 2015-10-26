//package ds.rug.nl.algorithm;
//
//import ds.rug.nl.TestingVars.TestingVariables;
//import ds.rug.nl.main.NodeInfo;
//import ds.rug.nl.network.DTO.TestDTO;
//import ds.rug.nl.testActors.TestActor;
//import java.net.UnknownHostException;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
///**
// *
// * @author angelo
// */
//public class LeaderAlgoTest {
//
//    private TestActor ta;
//    private NodeInfo lni1;
//    private NodeInfo rni2;
//
//    public LeaderAlgoTest() {
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
//        lni1 = new NodeInfo("192.168.1.1", "angeloLeft");
//        rni2 = new NodeInfo("192.168.1.2", "angeloRight");
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of LeaderElection method, of class LeaderAlgo.
//     */
//    @Test
//    public void testHandle() {
//        System.out.println("handle");
//        
//        LeaderAlgo laInstance = new LeaderAlgo(ta, 123, lni1, rni2);
//        TestAlgo testalgo = new TestAlgo(ta);
//        
//        TestDTO data = new TestDTO(TestingVariables.testString1, "angeloLeft");
//        testalgo.send(data, lni1);
//        
//        laInstance.handle(data);
//        
//        System.out.println(data.getNodeName());
//        
//        //laInstance.getNeighbour(Direction.LEFT);
//
////MulticastDTO bigData = new MulticastDTO(data, 5, cmdType.send);
//
//    }
//
//
//
//
//}
