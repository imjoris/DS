package ds.rug.nl.algorithm;

import ds.rug.nl.network.DTO.DTO;

/**
 *
 * @author angelo
 */
public class DTOSeq{
        public int sequenceNumber;
        public DTO dto;

        public DTOSeq(int sequenceNumber, DTO dto) {
            this.sequenceNumber = sequenceNumber;
            this.dto = dto;
        } 
    }