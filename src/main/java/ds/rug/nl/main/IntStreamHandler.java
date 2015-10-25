/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.StreamHandler;
import ds.rug.nl.network.DTO.StreamDTO;

/**
 *
 * @author Bart
 */
public class IntStreamHandler implements StreamHandler<Integer> {

    @Override
    public void receiveStream(StreamDTO<Integer> packet) {
        System.out.println(packet.data);
    }
    
}
