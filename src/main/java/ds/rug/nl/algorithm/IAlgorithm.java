/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.network.DTO.DTO;

/**
 *
 * @author joris
 */
public interface IAlgorithm {
    void handle(DTO message);
}
