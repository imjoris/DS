package ds.rug.nl.algorithm;

import ds.rug.nl.main.CommonClientData;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.COmulticastDTO;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Tuple;
import java.util.ArrayList;
import java.util.Iterator;

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
        clientData.cVector.incementValue(node.getNodeId());
        COmulticastDTO pckg = new COmulticastDTO(clientData.cVector, data);
        pckg = (COmulticastDTO)this.setDTONodeInfo(pckg);
        bmultiCast.sendMulticast(pckg);
    }

    public void receiveSmthg(COmulticastDTO msg) {
        VectorClock rcvVector = msg.getVectorClock();
        DTO data = msg.getMessage();

        if (clientData.cVector.isNext(rcvVector, msg.getSender())) {
            deliverDTO(data, msg.getSender());
        } else {
            holdBackQ.add(new Tuple(rcvVector, data, msg.getSender()));
        }
    }

    public void deliverDTO(DTO data, Integer sender) {
        this.node.getCmdMessageHandler().handleDTO(data);
        
        clientData.cVector.incementValue(sender);
        
        Iterator<Tuple> i = holdBackQ.iterator();
        while (i.hasNext()) {
            Tuple tmp = i.next();
            if (clientData.cVector.isNext(tmp.vectorClock, tmp.id)) {
                deliverDTO(data, tmp.id);
                i.remove();
            }
        }
    }

    @Override
    public void handleDTO(DTO message) {
        receiveSmthg((COmulticastDTO) message);
    }
}
