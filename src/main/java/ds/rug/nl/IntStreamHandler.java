/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

import ds.rug.nl.main.StreamHandler;
import ds.rug.nl.network.DTO.StreamDTO;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author Bart
 */
public class IntStreamHandler implements StreamHandler<Integer> {

    @Override
    public void receiveStream(StreamDTO<Integer> packet) {
        //new Logger().log(new LogRecord(Level.FINE, packet.data.toString()));
    }    
}