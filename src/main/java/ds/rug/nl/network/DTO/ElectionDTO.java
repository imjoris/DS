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
public class ElectionDTO extends DTO{
    public int candidate;
    public int phaseNumebr;
    public int hopCount;

    public ElectionDTO(int candidate, int phaseNumebr, int hopCount) {
        super();
        this.candidate = candidate;
        this.phaseNumebr = phaseNumebr;
        this.hopCount = hopCount;
    }

}
