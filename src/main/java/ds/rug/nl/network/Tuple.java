package ds.rug.nl.network;

import ds.rug.nl.algorithm.VectorClock;
import ds.rug.nl.network.DTO.DTO;

/**
 * A normal Python-style tuple.
 * @author angelo

 */
public class Tuple { 
  public final VectorClock vectorClock; 
  public final DTO dto; 
  public final Integer id;

    public Tuple(VectorClock vectorClock, DTO dto, Integer id) {
        this.vectorClock = vectorClock;
        this.dto = dto;
        this.id = id;
    }

} 
