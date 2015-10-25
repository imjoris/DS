package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.JoinAnnounceDTO;
import ds.rug.nl.network.DTO.JoinRequestDTO;
import ds.rug.nl.network.DTO.JoinResponseDTO;
import static ds.rug.nl.network.DTO.JoinResponseDTO.ResponseType.*;
import ds.rug.nl.network.DTO.TreeDTO;
import ds.rug.nl.network.DTO.TreeDTO.CmdType;
import ds.rug.nl.threads.CmdMessageHandler;
import ds.rug.nl.tree.TreeNode;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the following DTOs:
 *   JoinRequestDTO,
 *   JoinResponseDTO,
 *   TreeDTO,
 *   JoinAnnounceDTO
 * 
 * @author Bart
 */
public class JoinAlgo extends Algorithm {

    private TreeNode<NodeInfo> streamTree;
    private TreeNode<NodeInfo> thisNode;
    private final CoMulticast coMulticast;

    private final CountDownLatch joinLatch;
    private JoinResponseDTO joinResponse;
    private final CountDownLatch treeLatch;
    private TreeDTO treeResponse;

    public JoinAlgo(Node node,
            TreeNode<NodeInfo> streamTree,
            TreeNode<NodeInfo> thisNode,
            CoMulticast coMulticast) 
    {
        super(node);
        this.streamTree = streamTree;
        this.thisNode = thisNode;
        this.coMulticast = coMulticast;
        joinLatch = new CountDownLatch(1);
        treeLatch = new CountDownLatch(1);
    }
    
    public void registerDTOs(){
        CmdMessageHandler cmdHandler = this.node.getCmdMessageHandler();
        cmdHandler.registerAlgorithm(JoinRequestDTO.class, this);
        cmdHandler.registerAlgorithm(JoinResponseDTO.class, this);
        cmdHandler.registerAlgorithm(TreeDTO.class, this);
        cmdHandler.registerAlgorithm(JoinAnnounceDTO.class, this);
    }

    @SuppressWarnings("InfiniteRecursion")
    public void joinTree() throws UnknownHostException {
        getTree();

        Iterator<TreeNode<NodeInfo>> highestLeaves = streamTree.getHighestLeaves();
        while (highestLeaves.hasNext()) {
            TreeNode<NodeInfo> leaf = highestLeaves.next();
            NodeInfo leafNode = leaf.data;

            if (requestAttach(leafNode)) {
                thisNode = leaf.addChild(this.node.getNodeInfo());
                announceJoin();
            }
            return;
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
        // This feels like non-dynamic discovery
        String aPeer = node.getDnsAlgo().getDNSIps().get(0);
        
        TreeDTO req = new TreeDTO(CmdType.request, null);
        send(req, new NodeInfo(aPeer, null));
        try {
            treeLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.streamTree = treeResponse.streamTree;    
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
        return joinResponse.responseType == accepted;
    }

    @Override
    public void handle(DTO message) {
        if (message instanceof TreeDTO)
            handleTree((TreeDTO) message); 
        if (message instanceof JoinRequestDTO)
            handleRequest((JoinRequestDTO) message);
        if (message instanceof JoinResponseDTO)
            handleResponse((JoinResponseDTO) message);
        if (message instanceof JoinAnnounceDTO)
            handleAnnounce((JoinAnnounceDTO) message);        
    }

    // simple ugly implementation of a 'callback'
    private void handleResponse(JoinResponseDTO message) {
        joinResponse = message;
        joinLatch.countDown();
    }

    // TO-DO make this not crash when the node still has no tree
    private void handleRequest(JoinRequestDTO message) {
        if (this.fullNode()) {
            joinResponse = new JoinResponseDTO(denied);
        } else {
            this.addChild(message.nodeInfo);
            joinResponse = new JoinResponseDTO(accepted);
        }
        reply(joinResponse, message);
    }

    private boolean fullNode() {
        synchronized (thisNode) {
            return thisNode.children.size() >= 3;
        }
    }

    private void addChild(NodeInfo nodeInfo) {
        synchronized (thisNode) {
            thisNode.addChild(nodeInfo);
        }
    }

    private void handleTree(TreeDTO treeDTO) {
        if (treeDTO.cmd == CmdType.reply){
            treeResponse = treeDTO;
            treeLatch.countDown();
            // latch is set in this.getTree()
            return;
        }
        if (treeDTO.cmd == CmdType.request){
            TreeDTO response = new TreeDTO(CmdType.reply, streamTree);
            reply(response, treeDTO);
        }
        
    }

    private void announceJoin() {
        JoinAnnounceDTO msg = new JoinAnnounceDTO(thisNode.data, thisNode.parent.data);
        coMulticast.sendSmthg(msg);
    }

    private void handleAnnounce(JoinAnnounceDTO announcement) {
        streamTree.findTreeNode(announcement.parentNode).addChild(announcement.joinedNode);
    }
}
