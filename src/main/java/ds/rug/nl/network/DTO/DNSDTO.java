package ds.rug.nl.network.DTO;

import java.util.List;

/**
 *
 * @author joris
 */
public class DNSDTO extends DTO{
    public enum cmdType{
        request,
        response
    }
    public cmdType command;
    public List<String> ips;
}
