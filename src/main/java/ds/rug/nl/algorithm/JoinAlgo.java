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
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the following DTOs: JoinRequestDTO, JoinResponseDTO, NodeStateDTO,
 JoinAnnounceDTO
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
        String aPeer = discovery.getAPeer();
        treeLatch = new CountDownLatch(1);
        NodeStateDTO req = new NodeStateDTO(CmdType.request, null, null, null);
        send(req, aPeer);
        try {
            treeLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (clientData.streamTree == null) {
            clientData.streamTree = new TreeNode<NodeInfo>(new NodeInfo(treeResponse.ip, treeResponse.nodeName));
        } else {
            clientData.streamTree = treeResponse.streamTree;
        }
        
        clientData.bVector = treeResponse.bmvc;
        clientData.cVector = treeResponse.covc;
    }

    private boolean requestAttach(NodeInfo leafNode) {
        joinLatch = new CountDownLatch(1);
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
        if (this.fullNode()) {
            joinResponse = new JoinResponseDTO(denied, null);
        } else {
            this.addChild(message.nodeInfo);
            joinResponse = new JoinResponseDTO(accepted, leaderAl.getNeighbour());
        }
        reply(joinResponse, message);
    }

    private boolean fullNode() {
        synchronized (clientData) {
            return clientData.thisNode.children.size() >= 3;
        }
    }

    private void addChild(NodeInfo nodeInfo) {
        synchronized (clientData) {
            clientData.thisNode.addChild(nodeInfo);
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
            NodeStateDTO response = new NodeStateDTO(CmdType.reply, clientData.streamTree, clientData.bVector, clientData.cVector);
            reply(response, treeDTO);
        }

    }

    private void announceJoin() {
        JoinAnnounceDTO msg = new JoinAnnounceDTO(clientData.thisNode.contents,
                clientData.thisNode.parent.contents);
        coMulticast.sendSmthg(msg);
    }

    private void handleAnnounce(JoinAnnounceDTO announcement) {
        clientData.streamTree.findTreeNode(announcement.parentNode).addChild(announcement.joinedNode);
    }
}
