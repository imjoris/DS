package ds.rug.nl.algorithm;

import ds.rug.nl.main.CommonClientData;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.COmulticastDTO;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Tuple;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the following DTOs:
 * COmulticastDTO, 
 * 
 * @author angelo
 */
public class CoMulticast extends Algorithm {
    private ArrayList<Tuple> holdBackQ;
    private BMulticast bmultiCast;
    private final CommonClientData clientData;
    
    public CoMulticast(Node node, BMulticast bmulti, CommonClientData clientData) {
        super(node);
        this.bmultiCast = bmulti;
        this.clientData = clientData;
        this.holdBackQ = new ArrayList<Tuple>();

    }
    
    public void sendSmthg(DTO data) {
        clientData.cVector.incrementValue(node.getNodeId());
        COmulticastDTO pckg = new COmulticastDTO(clientData.cVector, data);
        this.setDTONodeInfo(pckg);
        System.out.println("Node " + node.getIpAddress() + " sending comulticast with vector " + clientData.cVector.get(node.getNodeId()).toString());
        bmultiCast.sendMulticast(pckg);
    }

    public void receiveSmthg(COmulticastDTO msg) {
        VectorClock rcvVector = msg.getVectorClock();
        DTO data = msg.getMessage();
        
        System.out.print(node.getIpAddress() + " received comulticast from " + msg.getIp() + " with vector " + msg.getVectorClock().toString() + ".");
        System.out.println(" Local vectorclock of node is " + clientData.cVector.toString());
        
        if (clientData.cVector.isNext(rcvVector, msg.getSender())) {
            System.out.println(node.getIpAddress() + " delivered comulti from " + msg.getIp());
            deliverDTO(data, msg.getSender());
        } else {
            holdBackQ.add(new Tuple(rcvVector, data, msg.getSender()));
        }
    }

    public synchronized void deliverDTO(DTO data, Integer sender) {
        
        this.node.getCmdMessageHandler().handleDTO(data);
        
        clientData.cVector.incrementValue(sender);
        
        Iterator<Tuple> i = holdBackQ.iterator();
        while (i.hasNext()) {
            Tuple tmp = i.next();
            if (clientData.cVector.isNext(tmp.vectorClock, tmp.id)) {
                i.remove();
                deliverDTO(tmp.dto, tmp.id);
            }
        }
    }
    
    @Override
    public void handleDTO(DTO message) {
        receiveSmthg((COmulticastDTO) message);
    }
}
