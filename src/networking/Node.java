/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author joris-main
 */
public class Node {
    
    public enum MessageType {
        GroupManagement 
    }
    private int port = 9000;
    private String ipAddress = "localhost";
    private String machineName = "";
    private static int threadCount = 0;
    
    /**
     * Constructor for Server
     */
    public Node(String name){
        this.machineName = name;
    }
    
    /**
     * Constructor for Client
     * @param ipAddress
     * @param name
     */
    public Node(String ipAddress, String name, int portNumber) {
        this.ipAddress = ipAddress;
        this.machineName = name;
        this.port = portNumber;
    }
    
    /**
     * Bind client socket
     * @throws IOException
     */
    protected void createClient() throws IOException{
        Socket socket = new Socket(this.ipAddress, this.port);
        SpawnConnectionThreads(socket);
    }
    
    /**
     * Bind server socket
     * @throws IOException
     */
    protected void createServer() throws IOException {
        ServerSocket server = new ServerSocket(9000);
        while(true){
        Socket s = server.accept();
        SpawnConnectionThreads(s);
        }
    }

    protected void SpawnConnectionThreads(Socket s) throws IOException{
        SendThread st = new SendThread(s, this.machineName);
        st.open();
        st.start();
        ReceiveThread rt = new ReceiveThread(s);
        rt.open();
        rt.start();
    }
    
    /**
     * Receive stream
     * @param socket
     * @throws IOException
     */
    protected void receiver(Socket socket) throws IOException{
        while(true){
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String answer = input.readLine();
            System.out.println(answer);
        }
    }
    
    /**
     * Send stream
     * @param socket
     * @throws IOException
     */
    protected void sender(Socket socket) throws IOException, InterruptedException{
        int count = 0;
        while (true) {
            Thread.sleep(1000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(count);
            count += 1;
        }
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
    

