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
    private final LeaderAlgo leaderAlgo;

    private CustomLatch joinLatch;
    private CountDownLatch hasAnnouncedLatch;
    private JoinResponseDTO joinResponse;
    private CustomLatch treeLatch;
    private NodeStateDTO treeResponse;
    private final Object requestSyncObject;
    private final Object announceSyncObject;
    private boolean isfirst;

    private LinkedList<JoinAnnounceDTO> joinAnnounceQueue;

    public JoinAlgo(Node node,
            CommonClientData clientData,
            CoMulticast coMulticast,
            DiscoveryAlgo discovery,
            LeaderAlgo leaderAlgo
    ) {
        this(node,
                clientData,
                coMulticast,
                discovery,
                leaderAlgo,
                false
        );
    }

    public JoinAlgo(Node node,
            CommonClientData clientData,
            CoMulticast coMulticast,
            DiscoveryAlgo discovery,
            LeaderAlgo leaderAlgo,
            boolean isfirst
    ) {

        super(node);
        this.isfirst = isfirst;
        this.clientData = clientData;
        this.coMulticast = coMulticast;
        this.discovery = discovery;
        this.leaderAlgo = leaderAlgo;
        this.joinAnnounceQueue = new LinkedList<JoinAnnounceDTO>();
        this.joinLatch = new CustomLatch();
        this.treeLatch = new CustomLatch();
        this.hasAnnouncedLatch = new CountDownLatch(1);
        this.requestSyncObject = new Object();
        this.announceSyncObject = new Object();
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
        try {
            getTree();
            processJoinAnnounceQueue();
            Iterator<TreeNode<NodeInfo>> highestLeaves = clientData.streamTree.getHighestLeaves();
            while (highestLeaves.hasNext()) {
                TreeNode<NodeInfo> leaf = highestLeaves.next();
                NodeInfo leafNode = leaf.contents;

                if ((!leafNode.equals(clientData.thisNode.contents)) && requestAttach(leafNode)) {
                    //clientData.thisNode = new TreeNode<NodeInfo>(this.node.getNodeInfo());
                    leaf.children.add(clientData.thisNode);
                    clientData.thisNode.parent = leaf;
                    //leaf.addChild(this.node.getNodeInfo());
                    announceJoin();
                    //leaderAlgo.requestInsert(leafNode);
                    return;
                }
            }

            // if we get here, none of the leaves were available, so we restart
            // this should be rare, so recursion should rarely happen and it is more
            // legible than a while (true)
            Thread.sleep(1000);
            System.out.println(node.getIpAddress() + " No leaves found");
            this.joinTree();
        } catch (InterruptedException ex) {
            Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets this.streamTree
     */
    private void getTree() throws UnknownHostException {
        String aPeer = discovery.getAPeer();

        NodeStateDTO req = new NodeStateDTO(CmdType.request, null, null, null);
        send(req, aPeer);
        treeLatch.await();
        //System.out.println("Node " + clientData.thisNode.contents.getIpAddress() + "received tree");
        clientData.streamTree = treeResponse.streamTree;

        //out.println("Received tree:");
        printTree();

        clientData.bVector = treeResponse.bmvc;
        clientData.cVector = treeResponse.covc;
    }

    private boolean requestAttach(NodeInfo leafNode) {

        JoinRequestDTO request = new JoinRequestDTO(this.node.getNodeInfo());
        //out.println(node.getIpAddress() + "is about to send a joinreq to " + leafNode.getIpAddress());
        send(request, leafNode);
        this.joinLatch = new CustomLatch();
        joinLatch.await();
        if (joinResponse.responseType == accepted) {
            //out.println("Node " + this.node.getIpAddress() + " is accepted to be a child of " + joinResponse.getIp());
        } else {
            //out.println("Node " + this.node.getIpAddress() + " is denied to be a child of " + joinResponse.getIp());
        }
        if (joinResponse.responseType == accepted) {
            return true;
        } else {
            return false;
        }
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
        joinLatch.notifyWaiter();
    }

    private void handleRequest(JoinRequestDTO message) {
        synchronized (requestSyncObject) {
            if (this.isFull()) {
                joinResponse = new JoinResponseDTO(denied);
                //out.println(node.getIpAddress() + " is denying " + message.getIp() + " as a child");
            } else {
                if (!isfirst) {
                    try {
                        //out.println(this.node.getIpAddress() + " = Before await");
                        hasAnnouncedLatch.await();
                        //System.out.println(this.node.getIpAddress() + " = After await");
                    } catch (Exception ex) {
                        //System.out.println("Working fine! :)");
                        Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                clientData.thisNode.addChild(message.requestingNode);
                joinResponse = new JoinResponseDTO(accepted);
                //System.out.println(node.getIpAddress() + " is accepting " + message.getIp() + " as a child");
                //System.out.println("message ip of joinrequest: " + message.getIp());
            }
            if (joinResponse == null) {
                System.out.println("joinresponse was null");
            }
            reply(joinResponse, message);
        }
    }

    private boolean isFull() {
        synchronized (clientData) {
            return clientData.thisNode.children.size() >= 3;
        }
    }

    private void handleTree(NodeStateDTO treeDTO) {
        if (treeDTO.cmd == CmdType.reply) {
            treeResponse = treeDTO;
            treeLatch.notifyWaiter();
            // latch is set in this.getTree()
            return;
        }
        if (treeDTO.cmd == CmdType.request) {
//            if (clientData.streamTree == null) {
//                return;
//            }
            NodeStateDTO response = new NodeStateDTO(CmdType.reply, clientData.streamTree, clientData.bVector, clientData.cVector);
            reply(response, treeDTO);
        }

    }

    private void announceJoin() {
        JoinAnnounceDTO msg = new JoinAnnounceDTO(
                clientData.thisNode.contents,
                clientData.thisNode.parent.contents
        );
        //System.out.println("Node " + node.getIpAddress() + " announcing being a child of " + clientData.thisNode.parent.contents.getIpAddress());
        coMulticast.sendSmthg(msg);
        //System.out.println(node.getIpAddress() + " is about to notify the anouncementwaiter");
        hasAnnouncedLatch.countDown();
    }

    public void printTree() {
        TreeNode<NodeInfo> node;
        node = clientData.streamTree;
        while (!node.isRoot()) {
            //System.out.println("Shits fucked up");
            node = node.parent;
        }
        printChildren(node, 0);
    }

    private void printChildren(TreeNode<NodeInfo> t, int ind) {
        if (t == null) {
            return;
        }
        String indent = "";
        for (int i = 0; i < ind; i++) {
            indent += "\t";
        }
        int i = 1;
        System.out.println(indent + "Info of Node " + t.contents.getIpAddress());
        System.out.println(indent + "NUM CHILDREN:" + t.children.size());

        for (TreeNode<NodeInfo> childNode : t.children) {
            System.out.println(indent + "INFO OF CHILD:" + i);
            System.out.println(indent + "\tCHILD" + i + " IP:" + childNode.contents.getIpAddress());
            System.out.println(indent + "\tCHILD" + i + " HOSTNAME:" + childNode.contents.getNodeName());
            printChildren(childNode, (ind + 1));
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
        synchronized (announceSyncObject) {
            if (clientData.streamTree == null) {
                System.out.println(node.getIpAddress() + " Queued an announcement");
                joinAnnounceQueue.add(announcement);
            }
            if (clientData.thisNode.contents.equals(announcement.parentNode)) {
                return;
            }
            System.out.println(node.getIpAddress() + " Processes announcement: parentnode=" + announcement.parentNode.getIpAddress() + " joinednode: " + announcement.joinedNode.getIpAddress());
            TreeNode<NodeInfo> treeNode = clientData.streamTree.findTreeNode(announcement.parentNode);
            if (treeNode == null) {
                try {
                    System.out.println(node.getIpAddress() + " is retrying to process the announce with a new tree");
                    Thread.sleep(500);
                    getTree();
                    handleAnnounce(announcement);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                NodeInfo announcedJoinedNode = announcement.joinedNode;
                TreeNode<NodeInfo> addChild = treeNode.addChild(announcedJoinedNode);
            }
            //}
            //LOGGERSTUFF
            //System.out.println("MY IP:" + clientData.thisNode.contents.getIpAddress());
            //printTree();
        }
    }
}
