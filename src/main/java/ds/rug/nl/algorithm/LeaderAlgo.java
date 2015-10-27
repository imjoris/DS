package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.ElectionDTO;
import ds.rug.nl.network.DTO.RingInsertDTO;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Handles the following DTOs: ElectionDTO
 *
 * @author angelo
 */
public class LeaderAlgo extends Algorithm {
    
    private NodeInfo nextNode;
    private NodeInfo currentLeader;
    
    private final CountDownLatch insertLatch;
    private RingInsertDTO insertMessage;
    private final ElectionLock electionLock;
    private boolean participant;

    private static class ElectionLock {
        private enum State {
            OPEN,
            ELECTION,
            JOINING,
        }
        
        Lock lock;
        Condition notElecting;
        Condition notHandling;
        State state;

        public ElectionLock() {
            this.lock = new ReentrantLock();
            this.notElecting = lock.newCondition();
            this.notHandling = lock.newCondition();
            
            this.state = State.OPEN;
        }
        
        public void EnterElection(){
            lock.lock();
            try {
                while (state != State.JOINING)
                    notHandling.await();
                state = State.ELECTION;
            } catch (InterruptedException ex) {
                Logger.getLogger(LeaderAlgo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                lock.unlock();
            }
        }
        
        public void LeaveElection(){
            lock.lock();
            if (state == State.ELECTION)
                state = State.OPEN;
            notElecting.signalAll();
            lock.unlock();
        }
        
         public void EnterJoining(){
            lock.lock();
            try {
                while (state != State.ELECTION)
                    notElecting.await();
                state = State.ELECTION;
            } catch (InterruptedException ex) {
                Logger.getLogger(LeaderAlgo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                lock.unlock();
            }
        }
        
        public void LeaveJoining(){
            lock.lock();
            if (state == State.JOINING)
                state = State.OPEN;
            notElecting.signalAll();
            lock.unlock();
        }                       
    }

    public LeaderAlgo(Node node) {
        super(node);
        nextNode = null;
        currentLeader = null;
        insertLatch = new CountDownLatch(1);
        electionLock = new ElectionLock();
    }
    
    public synchronized void requestInsert(NodeInfo node){
        send(new RingInsertDTO(RingInsertDTO.CmdType.REQUEST, this.node.getNodeInfo()), node);
        try {
            insertLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(LeaderAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
        // here we received the response in this.insertMessage
        this.nextNode = insertMessage.newNeighbour;        
    }

    @Override
    public void handleDTO(DTO message) {
        if (message instanceof ElectionDTO)
            handleElection((ElectionDTO) message);
        if (message instanceof RingInsertDTO)
            handleRingInsert((RingInsertDTO) message);

    }
    
    private void handleElection(ElectionDTO message){
        electionLock.EnterElection();
        switch (message.cmd){
            case ELECTION:                
                if (node.getNodeId() < message.BestNode.getNodeId())
                    send(message, nextNode);
                else if (node.getNodeId() == message.BestNode.getNodeId())
                    send(new ElectionDTO(node.getNodeInfo(), ElectionDTO.CmdType.LEADER), nextNode);
                else if (!participant)
                    send(new ElectionDTO(node.getNodeInfo(), ElectionDTO.CmdType.ELECTION), nextNode);
                participant = true;
            break;
            case LEADER:
                currentLeader = message.BestNode;
                if (node.getNodeId() != currentLeader.getNodeId())
                    send(message, nextNode);
                else
                    becomeLeader();
                participant = false;
                electionLock.LeaveElection();
            break;
        }
    }
    
    private synchronized void handleRingInsert(RingInsertDTO message){
        switch (message.cmd){
            case REQUEST:
                electionLock.EnterJoining();
                reply(new RingInsertDTO(RingInsertDTO.CmdType.ACCEPT, nextNode), message);
                nextNode = message.newNeighbour;
            break;
            case ACCEPT:
                insertMessage = message;
                insertLatch.countDown();
            break;
            case ACCEPT_ACK:
                electionLock.LeaveJoining();
            break;
        }
        
    }
    
    
    private void becomeLeader() {
        System.out.println("I AM GOOOOOOD\nwheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
}
