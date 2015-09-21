/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
    private String ipAddress = "localhost";
    private String machineName = "";

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
    protected void createClient() throws IOException {
        Socket socket = new Socket(this.ipAddress, PORT);
        SpawnConnectionThreads(socket);
    }

    /**
     * Bind server socket
     *
     * @throws IOException
     */
    protected void createServer() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        while (true) {
            Socket s = server.accept();
            SpawnConnectionThreads(s);
        }
    }

    /**
     * This will spawn 2 new threads for each connection one for receiving and
     * one for transmitting.
     *
     * @param socket
     * @throws IOException
     */
    private void SpawnConnectionThreads(Socket socket) throws IOException {
        Transmission st = new Transmission(socket, this.machineName, RCV);
        st.open();
        st.start();
        Transmission rt = new Transmission(socket, this.machineName, SND);
        rt.open();
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

