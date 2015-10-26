/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.tree;

import ds.rug.nl.main.NodeInfo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bart
 */
public class TreeNodeTest {
    
    TreeNode<NodeInfo> testTree;
    TreeNode<NodeInfo> aChild;
    
    public TreeNodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws UnknownHostException {
        testTree = new TreeNode<NodeInfo>(new NodeInfo("127.0.0.1", "testName"));
        testTree.addChild(new NodeInfo("192.168.255.255", "HEAR ME"));
        aChild = testTree.addChild(null)
                .addChild(new NodeInfo("0.0.0.0", "ALL SHALL HEAR"));        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isRoot method, of class TreeNode.
     */
    @Test
    public void testSerialize() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ObjectOutputStream oOut = new ObjectOutputStream(bOut) ;
        oOut.writeObject(testTree);
        
        ByteArrayInputStream bIn = new ByteArrayInputStream(bOut.toByteArray());
        ObjectInputStream oIn = new ObjectInputStream(bIn);
        TreeNode<NodeInfo> outTree = (TreeNode<NodeInfo>) oIn.readObject();
        
        Assert.assertFalse(outTree.children.isEmpty());
        Assert.assertEquals(makeChildrenSet(outTree), makeChildrenSet(testTree));
        Assert.assertNotNull(outTree.findTreeNode(aChild.contents));
    }
    
    private Set<NodeInfo> makeChildrenSet(TreeNode<NodeInfo> node){
        Set<NodeInfo> set = new HashSet<NodeInfo>();
        for (TreeNode<NodeInfo> child: node.children){
            set.add(child.contents);
        }
        return set;
    }
    
}
