package ds.rug.nl.algorithm;

import ds.rug.nl.main.CommonClientData;
import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.JoinAnnounceDTO;
import ds.rug.nl.network.DTO.JoinRequestDTO;
import ds.rug.nl.network.DTO.JoinResponseDTO;
import static ds.rug.nl.network.DTO.JoinResponseDTO.ResponseType.*;
import ds.rug.nl.network.DTO.NodeStateDTO;
import ds.rug.nl.network.DTO.NodeStateDTO.CmdType;
import ds.rug.nl.threads.CmdMessageHandler;
import ds.rug.nl.tree.TreeNode;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Queue;

/**
 * Handles the following DTOs: JoinRequestDTO, JoinResponseDTO, NodeStateDTO,
 * JoinAnnounceDTO
 *
 * @author Bart
 */
public class JoinAlgo extends Algorithm {

    private final CommonClientData clientData;
    private final CoMulticast coMulticast;
    private final DiscoveryAlgo discovery;
    private final LeaderAlgo leaderAl;

    private CountDownLatch joinLatch;
    private JoinResponseDTO joinResponse;
    private CountDownLatch treeLatch;
    private NodeStateDTO treeResponse;

    private LinkedList<JoinAnnounceDTO> joinAnnounceQueue;

    public JoinAlgo(Node node,
            CommonClientData clientData,
            CoMulticast coMulticast,
            DiscoveryAlgo discovery,
            LeaderAlgo leaderAl
    ) {
        super(node);
        this.clientData = clientData;
        this.coMulticast = coMulticast;
        this.leaderAl = leaderAl;
        this.discovery = discovery;
        this.joinAnnounceQueue = new LinkedList<JoinAnnounceDTO>();
        this.treeLatch = new CountDownLatch(1);
        this.joinLatch = new CountDownLatch(1);
    }

    public void registerDTOs() {
        CmdMessageHandler cmdHandler = this.node.getCmdMessageHandler();
        cmdHandler.registerAlgorithm(JoinRequestDTO.class, this);
        cmdHandler.registerAlgorithm(JoinResponseDTO.class, this);
        cmdHandler.registerAlgorithm(NodeStateDTO.class, this);
        cmdHandler.registerAlgorithm(JoinAnnounceDTO.class, this);
    }

    @SuppressWarnings("InfiniteRecursion")
    public void joinTree() throws UnknownHostException {
        getTree();

        Iterator<TreeNode<NodeInfo>> highestLeaves = clientData.streamTree.getHighestLeaves();
        while (highestLeaves.hasNext()) {
            TreeNode<NodeInfo> leaf = highestLeaves.next();
            NodeInfo leafNode = leaf.contents;

            if (!leafNode.equals(clientData.thisNode.contents) && requestAttach(leafNode)) {
                clientData.thisNode = leaf.addChild(this.node.getNodeInfo());
                announceJoin();
                return;
            }
            
        }

        // if we get here, none of the leaves were available, so we restart
        // this should be rare, so recursion should rarely happen and it is more
        // legible than a while (true)
        this.joinTree();
    }

    /**
     * Sets this.streamTree
     */
    private void getTree() throws UnknownHostException {
        String aPeer = discovery.getAPeer();
        
        NodeStateDTO req = new NodeStateDTO(CmdType.request, null, null, null);
        send(req, aPeer);
        try {
            treeLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Node " + clientData.thisNode.contents.getIpAddress() + "received tree");
        clientData.streamTree = treeResponse.streamTree;
        
        System.out.println("Received tree:");
        printTree(treeResponse.streamTree);
        
        processJoinAnnounceQueue();

        clientData.bVector = treeResponse.bmvc;
        clientData.cVector = treeResponse.covc;
    }

    private boolean requestAttach(NodeInfo leafNode) {
        
        JoinRequestDTO request = new JoinRequestDTO(this.node.getNodeInfo());
        send(request, leafNode);
        // Should be promise / future, perhaps on algorithm level
        try {
            // handleResponse will call notifyAll()
            joinLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(joinResponse.responseType == accepted){
            System.out.println("Node " + this.node.getIpAddress() + " is accepted to be a child of " + joinResponse.ip);
        }
        return joinResponse.responseType == accepted;
    }

    @Override
    public void handleDTO(DTO message) {
        if (message instanceof NodeStateDTO) {
            handleTree((NodeStateDTO) message);
        }
        if (message instanceof JoinRequestDTO) {
            handleRequest((JoinRequestDTO) message);
        }
        if (message instanceof JoinResponseDTO) {
            handleResponse((JoinResponseDTO) message);
        }
        if (message instanceof JoinAnnounceDTO) {
            handleAnnounce((JoinAnnounceDTO) message);
        }
    }

    // simple ugly implementation of a 'callback'
    private void handleResponse(JoinResponseDTO message) {
        joinResponse = message;
        joinLatch.countDown();
    }

    private void handleRequest(JoinRequestDTO message) {
        if (this.isFull()) {
            joinResponse = new JoinResponseDTO(denied, null);
        } else {
            clientData.thisNode.addChild(message.nodeInfo);
            joinResponse = new JoinResponseDTO(accepted, leaderAl.getNeighbour());
            System.out.println(node.getIpAddress()+ " is accepting " + message.getIp() + " as a child");
        }
        
        reply(joinResponse, message);
    }

    private boolean isFull() {
        synchronized (clientData) {
            return clientData.thisNode.children.size() >= 3;
        }
    }

    private void handleTree(NodeStateDTO treeDTO) {
        if (treeDTO.cmd == CmdType.reply) {
            treeResponse = treeDTO;
            treeLatch.countDown();
            // latch is set in this.getTree()
            return;
        }
        if (treeDTO.cmd == CmdType.request) {
            if (clientData.streamTree == null) {
                return;
            }
            NodeStateDTO response = new NodeStateDTO(CmdType.reply, clientData.streamTree, clientData.bVector, clientData.cVector);
            reply(response, treeDTO);
        }

    }

    private void announceJoin() {
        JoinAnnounceDTO msg = new JoinAnnounceDTO(
                clientData.thisNode.contents,
                clientData.thisNode.parent.contents
        );
        System.out.println("Node " + node.getIpAddress() + " announcing being a child of " + clientData.thisNode.parent.contents.getIpAddress());
        coMulticast.sendSmthg(msg);
    }

    private void printTree(TreeNode<NodeInfo> node){
        node = clientData.thisNode;
        while (!node.isRoot()) {
            printChildren(node, 0);
            node = node.parent;
        }
        printChildren(node, 0);
    }
    private void printChildren(TreeNode<NodeInfo> t, int ind) {
        if(t == null){
            return;
        }
        String indent="";
        for(int i =0; i < ind; i++){
            indent += "\t";
        }
        int i = 1;
        System.out.println(indent + "Info of Node " + t.contents.getIpAddress());
        System.out.println(indent + "NUM CHILDREN:" + t.children.size());
        
        for (TreeNode<NodeInfo> childNode : t.children) {
            System.out.println(indent + "INFO OF CHILD:" + i);
            System.out.println(indent + "\tCHILD" + i +" IP:" + childNode.contents.getIpAddress());
            System.out.println(indent + "\tCHILD" + i + " HOSTNAME:" + childNode.contents.getNodeName());
            printChildren(childNode, (ind+1));
            i++;
        }
    }
    
    private void processJoinAnnounceQueue() {
        for (JoinAnnounceDTO joinDto : joinAnnounceQueue) {
            if (clientData.streamTree.findTreeNode(joinDto.joinedNode) != null) {
                handleAnnounce(joinDto);
            }
        }
        joinAnnounceQueue.clear();
    }

    private void handleAnnounce(JoinAnnounceDTO announcement) {
        if (clientData.streamTree == null) {
            System.out.println(node.getIpAddress() + " Queued an announcement");
            joinAnnounceQueue.add(announcement);
        }
        if(clientData.thisNode.contents.equals(announcement.parentNode)){
            return;
        }
        
        TreeNode<NodeInfo> treeNode = clientData.streamTree.findTreeNode(announcement.parentNode);
        treeNode.addChild(announcement.joinedNode);
        System.out.println("MY IP:" + clientData.thisNode.contents.getIpAddress());
        printTree(clientData.streamTree);
    }
}
