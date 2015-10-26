package ds.rug.nl.network.DTO;

/**
 *
 * @author angelo
 */
public class ElectionRingDTO extends DTO{
    
    public enum cmdType{
        REQUEST,
        ACCEPT,
        DISCONNECT,
        NEWNEIGHBOUR,
        NEIGHBOURACKNOELEDGE
    }
    
    public enum Direction{
        LEFT,
        RIGHT;
    }
    
    public final cmdType cmd;
    public final Direction dir;

    public ElectionRingDTO(cmdType cmd, Direction dir) {
        this.cmd = cmd;
        this.dir = dir;
    }

}
