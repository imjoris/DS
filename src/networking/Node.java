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
    private static final int PORT = 9000;
    private String ipAddress = "localhost";
    
    /**
     * Constructor for Server
     */
    public Node(){

    }
    
    /**
     * Constructor for Client
     * @param ipAddress
     */
    public Node(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    
    /**
     * Bind client socket
     * @return Socket socket
     * @throws IOException
     */
    protected Socket createClient() throws IOException{
        Socket socket = new Socket(this.ipAddress, PORT);
        return socket;
    }
    
    /**
     * Bind server socket
     * @return Socket socket
     * @throws IOException
     */
    protected Socket createServer() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        Socket s = server.accept();
        return s;
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
    

