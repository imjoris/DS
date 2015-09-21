/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * THERE IS A BETTER WAY TO DO THIS WITH MULTICASTING. THIS IS ONLY TEMPORARY!!!!!!!!!!!!!!!!!!
 * @author Euaggelos
 */
public class Transmission extends Thread {

    private Socket socket = null;
    private String machineName = "";
    private int ID = -1;
    private DataInputStream streamIn = null;
    private States type;

    /**
     *
     * @param socket
     * @param machineName
     * @param type
     */
    public Transmission(Socket socket, String machineName, States type) {
        this.socket = socket;
        this.machineName = machineName;
        this.type = type;
        this.ID = socket.getPort();
    }

    @Override
    public void run() {
        System.out.println("Send Thread " + machineName + " " + ID + " running.");
        while (true) {
            try {
                switch (this.type) {
                    case RCV:
                        receiver(socket);
                        break;
                    case SND:
                        sender(socket);
                        break;
                    default:
                        break;
                }

            } catch (IOException ioe) {
            } catch (InterruptedException ex) {
                Logger.getLogger(Transmission.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @throws IOException
     */
    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    /**
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
    }

    /**
     *
     * @param socket
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void sender(Socket socket) throws IOException, InterruptedException {
        int count = 0;
        while (true) {
            Thread.sleep(1000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(this.machineName + " will now send");
            out.println(this.machineName + '\t' + count);
            count += 1;
        }
    }

    /**
     *
     * @param socket
     *
     * @throws IOException
     */
    protected void receiver(Socket socket) throws IOException {
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String answer = input.readLine();
            System.out.println(answer);
        }
    }

}
