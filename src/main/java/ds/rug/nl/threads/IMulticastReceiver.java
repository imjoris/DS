package ds.rug.nl.threads;

import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.MulticastDTO;

/**
 *
 * @author joris
 */
public interface IMulticastReceiver {
    void handleMulticastDTO(MulticastDTO dto);
}
