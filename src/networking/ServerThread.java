/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euaggelos
 */
public class ServerThread extends Thread {

    private Socket socket = null;
    private ServerNode server = null;
    private int ID = -1;
    private DataInputStream streamIn = null;

    public ServerThread(Socket _socket) {
        socket = _socket;
        ID = socket.getPort();
    }

    public void run() {
        System.out.println("Server Thread " + ID + " running.");
        while (true) {
            try {
                sender(socket);
                System.out.println(streamIn.readUTF());
            } catch (IOException ioe) {
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
    }
    
    public void sender(Socket socket) throws IOException, InterruptedException{
        System.out.println("asdf");
        int count = 0;
        while (true) {
            System.out.println("qwer");
            Thread.sleep(1000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(count);
            count += 1;
        }
    }
    
}
