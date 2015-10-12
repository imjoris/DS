package ds.rug.nl;

/**
 * Holding essential configurations.
 * @author joris
 */
public abstract class Config {
    public static final int multicastPort = 9006;
    public static final int commandPort = 9005;
    public static final int streamPort = 9004;
    public static final String multicastAdres = "224.0.0.3";
    public static final int bufferSize = 1024;
    public static final String dnsip = "192.168.1.2";
    
    public static class NodeNetwork{
        public static final String nextunusedip = "192.168.0.1";
        public static final String subnet = "255.255.255.0";
    }
}
