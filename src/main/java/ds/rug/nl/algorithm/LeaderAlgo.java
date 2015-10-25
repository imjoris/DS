package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.ElectionDTO;
import ds.rug.nl.network.DTO.ElectionReplyDTO;

/**
 * Handles the following DTOs:
 * ElectionDTO
 * 
 * @author angelo
 */
public class LeaderAlgo extends Algorithm {

    enum Direction{
        LEFT,
        RIGHT;
        
        public Direction getOpposite(){
            return this == LEFT ? RIGHT : LEFT;
        }
    }

    private NodeInfo leftNeighbour;
    private NodeInfo rightNeighbour;
    private int id;

    public LeaderAlgo(Node node, int id, NodeInfo leftNeighbour, NodeInfo rightNeighbour) {
        super(node);
        this.leftNeighbour = leftNeighbour;
        this.rightNeighbour = rightNeighbour;
        this.id = id;
    }

    public void LeaderElection() {

    }

    @Override
    public void handle(DTO message) {
        Direction dir = null;
        if (message.nodeName.equals(leftNeighbour))
            dir = Direction.LEFT;
        if (message.nodeName.equals(rightNeighbour))
            dir = Direction.RIGHT;
        if (dir == null)
            return;
        
        if (message instanceof ElectionDTO) {
            handleElection((ElectionDTO) message, dir);
        }

    }

    private void handleElection(ElectionDTO eleMsg, Direction dir) {
        if (eleMsg.candidate < id) //&& (eleMsg.hopCount < Math.pow(2, eleMsg.phaseNumebr)
            return;
        if (eleMsg.candidate == id) {
            anounceLeader();
            return;
        }
        if (eleMsg.hopCount > Math.pow(2, eleMsg.phaseNumebr))
                return;
        if (eleMsg.hopCount == Math.pow(2, eleMsg.phaseNumebr)){
            reply(eleMsg.candidate, eleMsg.phaseNumebr, dir.getOpposite());
            return;
        }
        
        passOn(eleMsg, dir);
        
    }

    public int anounceLeader() {
        return 0;
    }
    
    private NodeInfo getNeighbour(Direction dir){
        return dir == Direction.LEFT ? leftNeighbour : rightNeighbour;
    }

    private void reply(int candidate, int phaseNumebr, Direction dir) {
        ElectionReplyDTO message = new ElectionReplyDTO(candidate, phaseNumebr);
        send(message, getNeighbour(dir));
    }
    
    private void passOn(ElectionDTO eleMsg, Direction dir) {
        ElectionDTO message = new ElectionDTO(eleMsg.candidate, eleMsg.phaseNumebr, eleMsg.hopCount+1);
        send(message, getNeighbour(dir));
    }
}
