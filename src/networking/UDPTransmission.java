/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euaggelos
 */
public class UDPTransmission extends Thread {

    private DatagramSocket socket = null;
    private byte[] receiveData = new byte[1024];
    private byte[] sendData = new byte[1024];
    private InetAddress IPAddress = null;
    private int PORT = -1;
    private String machineName = "";
    private States type;

    public UDPTransmission(String machineName, DatagramSocket socket, States type) {
        this.socket = socket;
        this.machineName = machineName;
        this.type = type;
    }

    public UDPTransmission(String machineName, InetAddress IPAddress, DatagramSocket socket, States type) {
        this.socket = socket;
        this.machineName = machineName;
        this.type = type;
        this.IPAddress = IPAddress;
        
    }

    public void run() {
        System.out.println("Send Thread " + machineName + " UDP" + " running.");
        try {
            switch(this.type){
                case SND:
                    send();
                    break;
                case RCV:
                    receiver();
                    break;
                default:
                    break;
            }
            
        } catch (IOException ex) {
                    System.out.println("LOL");

            Logger.getLogger(UDPTransmission.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    protected void send() throws IOException {
        String str = "FUCK YOU";
        InputStream is = new ByteArrayInputStream(str.getBytes());
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(is));
        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        while(true){
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9005);
        this.socket.send(sendPacket);
        }
    }
    
    protected void receiver() throws IOException {
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(this.receiveData, this.receiveData.length);
            this.socket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);
        }
    }

}
