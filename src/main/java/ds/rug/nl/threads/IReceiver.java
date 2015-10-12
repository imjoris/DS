/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.threads;

import ds.rug.nl.network.ReceivedMessage;

/**
 *
 * @author joris
 */
public interface IReceiver {
    void handleMessage(ReceivedMessage message);
}
