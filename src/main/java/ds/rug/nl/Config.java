/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;

/**
 *
 * @author joris
 */
public class Config {
    public static int multicastPort = 9006;
    public static int commandPort = 9005;
    public static int streamPort = 9004;
    public static String multicastAdres = "224.0.0.3";
    public static int bufferSize = 1024;
    public static String dnsip = "192.168.1.2";
    
    public static class NodeNetwork{
        public static String nextunusedip="192.168.0.1";
        public static String subnet="255.255.255.0";
    }
}
