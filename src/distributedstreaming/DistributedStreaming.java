/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedstreaming;

import networking.ClientNode;
import networking.ServerNode;

/**
 *
 * @author joris-main
 */
public class DistributedStreaming {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread sn = new Thread(new ServerNode("server1"));
        Thread cn = new Thread(new ClientNode("localhost", "client1"));
        //Thread cn2 = new Thread(new ClientNode("localhost", "client2"));

        sn.start();
        cn.start();
        //cn2.start();

    }

}
