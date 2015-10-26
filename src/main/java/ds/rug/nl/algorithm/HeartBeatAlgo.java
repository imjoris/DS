package ds.rug.nl.algorithm;

import ds.rug.nl.main.Config;
import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.GeneralHeartBeatDTO;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angelo
 */
public class HeartBeatAlgo extends Algorithm {
    
    private final CountDownLatch beatLatch;

    public HeartBeatAlgo(Node node) {
        super(node);
        beatLatch = new CountDownLatch(1);

    }

    public Boolean sendHeartBeat(DTO message){
        GeneralHeartBeatDTO msg = (GeneralHeartBeatDTO) message;
        send(message, node.getNodeInfo());

        try {
            beatLatch.await(Config.timeout, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(JoinAlgo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public void replyHeartBeat(DTO message){
        reply(new GeneralHeartBeatDTO(generateRandomSeq(),GeneralHeartBeatDTO.ReplySend.REPLY), message);
    }
    
    private String generateRandomSeq(){
        Random random = new Random(10000);
        String seq = "";
        for(int i = 0; i < 5; i++){
            seq = seq.concat(Integer.toString(random.nextInt(9)));
        }
        return seq;
    }
    
    @Override
    public void handleDTO(DTO message) {
        GeneralHeartBeatDTO heartBeatmsg = (GeneralHeartBeatDTO) message;
        if(heartBeatmsg.msgType.equals(GeneralHeartBeatDTO.ReplySend.REPLY)){
            doSomething(heartBeatmsg);
            beatLatch.countDown();
        }
        else if(heartBeatmsg.msgType.equals(GeneralHeartBeatDTO.ReplySend.SEND))
            replyHeartBeat(heartBeatmsg);
    }
    
    public void doSomething(DTO msg){
        if(sendHeartBeat(msg)){
            
        }else{
            
        }
    }

}
