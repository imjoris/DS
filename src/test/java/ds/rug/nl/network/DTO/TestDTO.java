package ds.rug.nl.network.DTO;


/**
 *
 * @author angelo
 */
public class TestDTO extends DTO{
    public final String testString;

    public TestDTO(String testString, String nodeName) {
        super();
        this.testString = testString;
        this.setNodeName(nodeName);

    }
    
}
