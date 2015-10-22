package ds.rug.nl;

import ds.rug.nl.main.Node;

/**
 *
 * @author joris
 */
public class Server extends Node {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    
    public Server(){
        System.out.println("Creating server");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
