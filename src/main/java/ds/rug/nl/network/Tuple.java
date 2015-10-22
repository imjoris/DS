package ds.rug.nl.network;

import ds.rug.nl.network.DTO.DTO;

/**
 * A normal Python-style tuple.
 * @author angelo

 */
public class Tuple { 
  public final VectorClock vectorClock; 
  public final DTO dto; 
  public final String id;

    public Tuple(VectorClock vectorClock, DTO dto, String id) {
        this.vectorClock = vectorClock;
        this.dto = dto;
        this.id = id;
    }
  
  
 
} 
