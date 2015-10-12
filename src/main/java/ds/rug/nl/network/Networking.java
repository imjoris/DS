/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network;

import ds.rug.nl.Config;
import ds.rug.nl.network.DTO.*;
import ds.rug.nl.threads.IReceiver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class Networking {

    ServerSocket serversock;
    final String myip="192.168.0.1";
    InetAddress addr;
    SocketAddress from;
    
    public Networking() {
        try {
            InetAddress addr = InetAddress.getByName(myip);
            from = new InetSocketAddress(myip, Config.commandPort);
            InetAddress locIP = InetAddress.getByName("192.168.0.1");

            serversock=new ServerSocket(Config.commandPort, 0, locIP); 
    //serversock = new ServerSocket(Config.commandPort, 50, addr);
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(String json, String ip, int port) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(ip, port);
            Socket s = new Socket();
            
            s.bind(from);
            
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(json);
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendCommand(String message, String ip) {
        send(message, ip, Config.commandPort);
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

    public void startReceiving(int port, IReceiver handler) {

        class mythread extends Thread {

            IReceiver handler;

            public mythread(IReceiver handler) {
                this.handler = handler;
            }

            public void run() {
                try {
                    while (true) {
                        InetAddress addr = InetAddress.getByName(myip);
                        ServerSocket serversock = new ServerSocket(Config.commandPort, 50, addr);
                        Socket callsocket = serversock.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(callsocket.getInputStream()));
                        String jsonMessage = in.readLine();
                        ReceivedMessage message = new ReceivedMessage();
                        message.data = jsonMessage;
                        message.ip = callsocket.getLocalAddress().getHostAddress();
                        message.port = callsocket.getPort();
                        new mythread2(this.handler, message).start();
                        handler.handleMessage(message);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             class mythread2 extends Thread{
                IReceiver handler;
                ReceivedMessage message;
                public mythread2(IReceiver handler,ReceivedMessage message){
                    this.handler=handler;
                    this.message=message;
                }
                public void run(){
                    handler.handleMessage(message);
                }
            }
        }
    }

//    public ReceivedMessage receive(int port) {
//        ReceivedMessage message = new ReceivedMessage();
//        try {
//            Socket callsocket = serversock.accept();
//            BufferedReader in = new BufferedReader(new InputStreamReader(callsocket.getInputStream()));
//            String jsonMessage = in.readLine();
//            message.data = jsonMessage;
//            message.out = callsocket.getOutputStream();
//                
////            byte[] buffer = packet.getData();
////            
////            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
////            datagramSocket.receive(packet);
////            strData = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket
//        } catch (IOException ex) {
//            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return message;
//    }
    public void sendMulticast(String data) {
        byte[] sendData = data.getBytes();

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

    public void joinMulticastGroup(String ip, int port) {
        try {
            MulticastSocket socket;
            socket = new MulticastSocket(Config.multicastPort); // must bind receive side
            socket.joinGroup(InetAddress.getByName(Config.multicastAdres));
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DTO receiveMulticasts() {
        byte[] b = new byte[Config.bufferSize];
        DatagramPacket dgram = new DatagramPacket(b, b.length);
        MulticastSocket socket;
        ReceivedMessage message = new ReceivedMessage();
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
                message.data = strData;
//                message.out = socket.get
//                        = MulticastDTO multicastData = gson.fromJson(strData, MulticastDTO.class);
//                dto = multicastData;
            }
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
        //TODO
        //return dto;
    }
}
