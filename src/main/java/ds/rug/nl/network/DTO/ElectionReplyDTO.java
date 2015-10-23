/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

/**
 *
 * @author angelo
 */
public class ElectionReplyDTO extends DTO {
    private int id;
    private int phaseNumber;

    public ElectionReplyDTO(int id, int phaseNumber) {
        super();
        this.id = id;
        this.phaseNumber = phaseNumber;
    }
    
    
    
}
