/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl;
import org.junit.Test;

/**
 *
 * @author joris
 */
public class GlobalTest {

    @Test
    public void test() throws InterruptedException{
        Source.main(new String[] {"myhost1", "127.0.0.2"});
        for(int i = )
        App.main(new String[] {"myclient1", "127.0.0.3"});
        App.main(new String[] {"myclient2", "127.0.0.4"});
        while(true){}
    }
}
