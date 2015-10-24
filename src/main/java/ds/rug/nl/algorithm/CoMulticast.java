package ds.rug.nl.algorithm;

import ds.rug.nl.algorithm.Algorithm;
import ds.rug.nl.algorithm.BMulticast;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.COmulticastDTO;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.Tuple;
import ds.rug.nl.network.VectorClock;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author angelo
 */
public class CoMulticast extends Algorithm {

    private VectorClock localVector; // <ID, Counter>
    private ArrayList<Tuple> holdBackQ;
    private String id;
    private BMulticast bmultiCast;
    public CoMulticast(Node node, BMulticast bmulti) {
        super(node);
        this.bmultiCast = bmulti;
    }

    public void sendSmthg(DTO data) {

        COmulticastDTO pckg = new COmulticastDTO(localVector, data, id);
        bmultiCast.sendMulticast(pckg);
    }

    public void receiveSmthg(COmulticastDTO msg) {
        VectorClock rcvVector = msg.getVectorClock();
        DTO data = msg.getMessage();

        if (localVector.isNext(rcvVector, msg.getSender())) {
            deliverDTO(data, msg.getSender());
        } else {
            holdBackQ.add(new Tuple(rcvVector, data, msg.getSender()));
        }
    }

    public void deliverDTO(DTO data, String sender) {
        //TO DO
        localVector.incementValue(sender);

        Iterator<Tuple> i = holdBackQ.iterator();
        while (i.hasNext()) {
            Tuple tmp = i.next();
            if (localVector.isNext(tmp.vectorClock, tmp.id)) {
                deliverDTO(data, tmp.id);
                i.remove();
            }
        }
    }

    @Override
    public void handle(DTO message) {
        COmulticastDTO msg = (COmulticastDTO) message;
    }
}
