package ds.rug.nl.threads;

import ds.rug.nl.network.DTO.DTO;

/**
 *
 * @author joris
 */
public interface IReceiver {
    void handleDTO(DTO dto);
}
