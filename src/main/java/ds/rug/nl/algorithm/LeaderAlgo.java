package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.ClaimLeadershipDTO;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.ElectionDTO;
import ds.rug.nl.network.DTO.ElectionReplyDTO;
import ds.rug.nl.network.DTO.ElectionRingDTO;
import ds.rug.nl.threads.CmdMessageHandler;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the following DTOs: ElectionDTO
 *
 * @author angelo
 */
public class LeaderAlgo extends Algorithm {

    enum Direction {

        LEFT,
        RIGHT;

        public Direction getOpposite() {
            return this == LEFT ? RIGHT : LEFT;
        }
    }

    private NodeInfo leftNeighbour;
    private NodeInfo rightNeighbour;
    private int id;
    private NodeInfo leader;
    private BMulticast bMulticast;
    private CountDownLatch electionLatch;
    private Boolean inElection;

    public LeaderAlgo(Node node, BMulticast bMulticast) {
        super(node);
        this.leftNeighbour = null;
        this.rightNeighbour = null;
        this.id = node.getNodeId();
        this.leader = null;
        this.bMulticast = bMulticast;
        this.inElection = false;
        this.electionLatch = new CountDownLatch(1);
    }

    public void LeaderElection() {

    }

    public void registerDTOs() {
        CmdMessageHandler cmdHandler = this.node.getCmdMessageHandler();
        cmdHandler.registerAlgorithm(ElectionDTO.class, this);
        cmdHandler.registerAlgorithm(ElectionReplyDTO.class, this);
        cmdHandler.registerAlgorithm(ClaimLeadershipDTO.class, this);
        cmdHandler.registerAlgorithm(ElectionRingDTO.class, this);
    }

    @Override
    public void handle(DTO message) {
        Direction dir = null;
        if (message.nodeName.equals(leftNeighbour.getName())) {
            dir = Direction.LEFT;
        }
        if (message.nodeName.equals(rightNeighbour.getName())) {
            dir = Direction.RIGHT;
        }
        if (dir == null) {
            return;
        }

        if (message instanceof ElectionDTO) {
            handleElection((ElectionDTO) message, dir);
        }
        if (message instanceof ClaimLeadershipDTO) {
            ClaimLeadershipDTO msg = (ClaimLeadershipDTO) message;
            leader = msg.leaderNode;
        }
        if (message instanceof ElectionRingDTO) {
            handleRingJoin((ElectionRingDTO) message);
        }
    }

    @SuppressWarnings("empty-statement")
    private void handleRingJoin(ElectionRingDTO message) {
        switch (message.cmd) {
            case REQUEST:
                while (inElection) {
                    try {
                        electionLatch.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LeaderAlgo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                synchronized (this) {
                    if (message.dir == ElectionRingDTO.Direction.LEFT) {
                        send(new ElectionRingDTO(ElectionRingDTO.cmdType.NEWNEIGHBOUR, null), leftNeighbour);
                        leftNeighbour = new NodeInfo(message);
                    } else {
                        send(new ElectionRingDTO(ElectionRingDTO.cmdType.NEWNEIGHBOUR, null), rightNeighbour);
                        rightNeighbour = new NodeInfo(message);
                    }
                    reply(new ElectionRingDTO(ElectionRingDTO.cmdType.ACCEPT, null), message);
                }
                break;
            case ACCEPT:
                
                break;
            case DISCONNECT:
                
                break;
            case NEWNEIGHBOUR:
                
                break;
            case NEIGHBOURACKNOELEDGE:
                
                break;
                
        }

    }

    public void joinRing(NodeInfo leftNeighbour, NodeInfo rightNeighbour) {

    }

    public NodeInfo getLeader() {
        return leader;
    }

    private void handleElection(ElectionDTO eleMsg, Direction dir) {
        synchronized (this) {
            if (eleMsg.candidate < id) //&& (eleMsg.hopCount < Math.pow(2, eleMsg.phaseNumebr)
            {
                return;
            }
            if (eleMsg.candidate == id) {
                claimLeader();
                return;
            }
            if (eleMsg.hopCount > Math.pow(2, eleMsg.phaseNumebr)) {
                return;
            }
            if (eleMsg.hopCount == Math.pow(2, eleMsg.phaseNumebr)) {
                reply(eleMsg.candidate, eleMsg.phaseNumebr, dir.getOpposite());
                return;
            }

            passOn(eleMsg, dir);
        }
    }

    public void claimLeader() {

        ClaimLeadershipDTO message = new ClaimLeadershipDTO(node.getNodeInfo());
        bMulticast.sendMulticast(message);

    }

    private NodeInfo getNeighbour(Direction dir) {
        return dir == Direction.LEFT ? leftNeighbour : rightNeighbour;
    }

    private void reply(int candidate, int phaseNumebr, Direction dir) {
        ElectionReplyDTO message = new ElectionReplyDTO(candidate, phaseNumebr);
        send(message, getNeighbour(dir));
    }

    private void passOn(ElectionDTO eleMsg, Direction dir) {
        ElectionDTO message = new ElectionDTO(eleMsg.candidate, eleMsg.phaseNumebr, eleMsg.hopCount + 1);
        send(message, getNeighbour(dir));
    }

    public NodeInfo getNeighbour() {
        return this.rightNeighbour;
    }
}
