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
