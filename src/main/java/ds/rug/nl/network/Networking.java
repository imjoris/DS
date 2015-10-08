/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network;

import com.google.gson.Gson;
import ds.rug.nl.Config;
import ds.rug.nl.network.DTO.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class Networking {
    Gson gson;
    ServerSocket serversock;
    Map<Integer, MulticastDTO> sendMultiMessages;
    
    public Networking(){
        gson = new Gson();
        try {
            serversock = new ServerSocket(Config.commandPort);
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendMultiMessages = new HashMap<Integer, MulticastDTO>();
    }
    
    public void send(DTO message, String ip, int port) {
        String json = gson.toJson(message);
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//       
//        //String bytes = 
//        InetAddress address;
//        
//        try {
//            DatagramSocket socket;
//            socket = new DatagramSocket();
//            address = InetAddress.getByName(ip);
//            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
//            socket.send(packet);
//        }catch (UnknownHostException | SocketException ex) {
//            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
    
    public DTO receive(int port){
        DTO dto = null;
        try {
            Socket connectionSocket = serversock.accept();
            Socket socket = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String jsonMessage = in.readLine();
            dto = gson.fromJson(jsonMessage, DTO.class);
            
//            byte[] buffer = packet.getData();
//            
//            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//            datagramSocket.receive(packet);
//            strData = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket
                    } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dto;
    }

    public void sendMulticast(MulticastDTO dto) {
        String message = gson.toJson(dto);
        byte[] sendData = message.getBytes(); 
        
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket dgram;
            dgram = new DatagramPacket(
                        sendData,
                        sendData.length,
                        InetAddress.getByName(Config.multicastAdres),
                        Config.multicastPort
                );
                socket.send(dgram);
                sendMultiMessages.put(dto.getSequencenum(), dto);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void joinGroup(String ip, int port) {
        MulticastSocket socket; 
        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(InetAddress.getByName(ip));
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public DTO receiveMulticasts() {
        byte[] b = new byte[Config.bufferSize];
        DatagramPacket dgram = new DatagramPacket(b, b.length);
        MulticastSocket socket;
        DTO dto = null;
        try {
            socket = new MulticastSocket(Config.multicastPort); // must bind receive side
            socket.joinGroup(InetAddress.getByName(Config.multicastAdres));
            while (true) {
                socket.receive(dgram); // blocks until a datagram is received
                System.err.println("Received " + dgram.getLength()
                        + " bytes from " + dgram.getAddress());
                dgram.setLength(b.length); // must reset length field!
                String strData = new String(
                        dgram.getData(),
                        dgram.getOffset(),
                        dgram.getLength()
                );
                MulticastDTO multicastData = gson.fromJson(strData, MulticastDTO.class);
                dto=multicastData;
            }
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);

        }
        return dto;
    }
}
