/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

import ds.rug.nl.network.DTO.StreamDTO;

/**
 *
 * @author Bart
 */
public interface StreamHandler<T> {
    public void receiveStream(StreamDTO<T> packet);        
}
