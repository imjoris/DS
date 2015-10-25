/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

/**
 *
 * @author Bart
 */
public class StreamDTO<T> extends DTO {
    public final T data;

    public StreamDTO(T data) {
        this.data = data;
    }
    
}
