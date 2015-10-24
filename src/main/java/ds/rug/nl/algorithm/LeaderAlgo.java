/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.ElectionDTO;
import ds.rug.nl.network.DTO.ElectionReplyDTO;
import java.net.InetSocketAddress;

/**
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

    private String leftNeighbour;
    private String rightNeighbour;
    private InetSocketAddress leftAddress;
    private InetSocketAddress rightAddress;
    private int id;

    public LeaderAlgo(Node node, int id, String leftNeighbour, String rightNeighbour, InetSocketAddress leftAddress, InetSocketAddress rightAddress) {
        super(node);
        this.leftNeighbour = leftNeighbour;
        this.rightNeighbour = rightNeighbour;
        this.rightAddress = rightAddress;
        this.leftAddress = leftAddress;
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
    
    private InetSocketAddress getAddress(Direction dir){
        return dir == Direction.LEFT ? leftAddress : rightAddress;
    }

    private void reply(int candidate, int phaseNumebr, Direction dir) {
        ElectionReplyDTO message = new ElectionReplyDTO(candidate, phaseNumebr);
        send(message, getAddress(dir));
    }
    
    private void passOn(ElectionDTO eleMsg, Direction dir) {
        ElectionDTO message = new ElectionDTO(eleMsg.candidate, eleMsg.phaseNumebr, eleMsg.hopCount+1);
        send(message, getAddress(dir));
    }
}
