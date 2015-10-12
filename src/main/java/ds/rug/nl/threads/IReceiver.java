package ds.rug.nl.threads;

import ds.rug.nl.network.ReceivedMessage;

/**
 *
 * @author joris
 */
public interface IReceiver {
    void handleMessage(ReceivedMessage message);
}
