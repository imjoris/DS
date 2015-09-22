/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import static networking.States.*;

/**
 *
 * @author joris-main
 */
public class Node {

    public enum MessageType {

        GroupManagement
    }

    private final int PORT = 9000;
    private final int PORT2 = 9005;
    private String ipAddress = "localhost";
    private String machineName = "";
    
    private  byte[] sendData = new byte[1024];
    private byte[] receiveData = new byte[1024];
    /**
     * Constructor for Server
     *
     * @param machineName
     */
    public Node(String machineName) {
        this.machineName = machineName;
    }

    /**
     * Constructor for Client
     *
     * @param ipAddress
     * @param machineName
     */
    public Node(String ipAddress, String machineName) {
        this.ipAddress = ipAddress;
        this.machineName = machineName;
    }

    /**
     * Bind client socket
     *
     * @throws IOException
     */
    protected void createTCPClient() throws IOException {
        Socket socket = new Socket(this.ipAddress, PORT);
        spawnTCPConnectionThreads(socket);
    }

    /**
     * Bind server socket
     *
     * @throws IOException
     */
    protected void createTCPServer() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        while (true) {
            Socket s = server.accept();
            spawnTCPConnectionThreads(s);
        }
    }
    
    protected void createUDPServer() throws SocketException, IOException{
        DatagramSocket socket = new DatagramSocket(this.PORT2);
        String sentence = "Send something";
        String tmp;
        sendData = sentence.getBytes();
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(this.receiveData, this.receiveData.length);
            socket.receive(receivePacket);
            tmp = new String(receivePacket.getData());
            System.out.println(tmp);
//            sentence += tmp;
//            InetAddress IPAddress = receivePacket.getAddress();
//            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9005);
//            socket.send(sendPacket);

            //spawnUDPConnectionThreads(IPAddress, socket);
        }
    }
    
    protected void createUDPClient() throws UnknownHostException, SocketException, IOException{
        DatagramSocket socket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        
        String str;
        String tmp;
        int count = 0;
        while(true){
            tmp = Integer.toString(count);
            sendData = tmp.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9005);
            socket.send(sendPacket);
            count += 1;
        }
        //spawnUDPConnectionThreads(IPAddress, socket);
    }

    /**
     * This will spawn 2 new threads for each connection one for receiving and
     * one for transmitting.
     *
     * @param socket
     * @throws IOException
     */
    private void spawnTCPConnectionThreads(Socket socket) throws IOException {
        TCPTransmission st = new TCPTransmission(socket, this.machineName, SND);
        st.start();
        TCPTransmission rt = new TCPTransmission(socket, this.machineName, RCV);
        rt.start();
    }
    
    private void spawnUDPConnectionThreads(DatagramSocket socket){
        UDPTransmission st = new UDPTransmission(this.machineName, socket, SND);
        st.start();
        UDPTransmission rt = new UDPTransmission(this.machineName, socket, RCV);
        rt.start();
    }
    
    private void spawnUDPConnectionThreads(InetAddress IPAddress, DatagramSocket socket){
        UDPTransmission st = new UDPTransmission(this.machineName, IPAddress, socket, SND);
        st.start();
        UDPTransmission rt = new UDPTransmission(this.machineName, IPAddress, socket, RCV);
        rt.start();
    }
}

    //public void receiveMessage(){
//Get message and check the MessageType
//Then dispatch it to the corresponding algorithm?
//        //MessageType mType = MessageType.GroupManagement;
//        switch(mType){
//            case GroupManagement:
//                  IAlgorithm algorithm = new GroupAlgorithm();
//                    algorithm.handle(message);
//                break;
//                
//        }
// }

