/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

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
public class TCPTransmission extends Thread {

    private Socket socket = null;
    private String machineName = "";
    private int ID = -1;
    private DataInputStream streamIn = null;
    private final States type;

    /**
     *
     * @param socket
     * @param machineName
     * @param type
     */
    public TCPTransmission(Socket socket, String machineName, States type) {
        this.socket = socket;
        this.machineName = machineName;
        this.type = type;
        this.ID = socket.getPort();
    }

    @Override
    public void run() {
        System.out.println("Send Thread " + machineName + " " + ID + " running.");
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
                Logger.getLogger(TCPTransmission.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     *
     * @throws IOException
     */
    public void close() throws IOException {
        System.out.println("thread will now terminate");
        if (socket != null) {
            socket.close();
        }
    }

    /**
     * @param socket
     *
     * @throws IOException
     * @throws InterruptedException
     */
    protected void sender(Socket socket) throws IOException, InterruptedException {
        boolean running = true;
        int count = 0;//TO BE REMOVED
        while (running) {
            Thread.sleep(1000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(this.machineName + " will now send");
            out.println(this.machineName + '\t' + count);
            count += 1;//TO BE REMOVED
            if(count == 20){
                out.println("TERMINATE");
                running = false;
            }
        }
        close();
    }

    /**
     *
     * @param socket
     *
     * @throws IOException
     * @throws java.lang.InterruptedException
     */
    protected void receiver(Socket socket) throws IOException, InterruptedException {
        boolean running = true;
        String answer = "";
        int timer = 1000;
        int timeout = 0, timeoutLimit = 10000;
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (running) {
            if(input.ready()){
                timeout = 0;
                answer = input.readLine();
                System.out.println(answer);
            }
            else{
                Thread.sleep(timer);
                timeout += timer;
            }
            
            if((timeout > timeoutLimit) || ("TERMINATE".equals(answer))){
                running = false;
            }
        }
        close();
    }

}
