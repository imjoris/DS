/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris-laptop
 */
public class CustomLatch {

    CountDownLatch myLatch;

    public CustomLatch() {
        myLatch = new CountDownLatch(0);
    }

    public synchronized void await() {
        try {
            myLatch = new CountDownLatch(1);
            myLatch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(CustomLatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void notifyWaiter() {
        notifyWaiter(null);
    }

    public void notifyWaiter(String message) {
        myLatch.countDown();
        if (message != null) {
            System.out.println(message + " notified the waiter");
        }

    }
}
