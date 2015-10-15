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
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joris
 */
public class Networking {

    Executor executor;
    public Networking() {
         executor = Executors.newFixedThreadPool(5);
    }

    public void send(String json, String ip, int port) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            //socket = new Socket(port);
            socket = new Socket(ip, port);

//            InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
//            s.bind(socketAddress);
//            s.setReuseAddress(true);
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


    public void startReceiving(String ip, int port, IReceiver handler) {

        class mythread extends Thread {

            IReceiver handler;
            String ip;
            int port;

            public mythread(String ip, int port, IReceiver handler) {
                this.handler = handler;
                this.ip = ip;
                this.port = port;
            }

            public void run() {
                try {
                    ServerSocket serversock = new ServerSocket();//Config.commandPort, 50, InetAddress.getByName(ip));
                    serversock.setReuseAddress(false);
                    
                    serversock.bind(new InetSocketAddress(ip, Config.commandPort));
                    System.out.println("Waiting for client on " + serversock.getInetAddress().getHostName() + " port "
                            + serversock.getLocalPort() + "...");
                    
                     ExecutorService executor = Executors.newFixedThreadPool(5);

                    while (true) {
                        try {
                            Socket callsocket = serversock.accept();
                            System.out.println("Just connected to "
                                    + callsocket.getRemoteSocketAddress());
                            BufferedReader in = new BufferedReader(new InputStreamReader(callsocket.getInputStream()));
                            String jsonMessage = in.readLine();
                            ReceivedMessage message = new ReceivedMessage();
                            
                            message.data = jsonMessage;
                            message.ip = callsocket.getLocalAddress().getHostAddress();
                            message.port = callsocket.getPort();
                            
                            Thread handleThreat = new mythread2(this.handler, message);
                            executor.execute(handleThreat);
                            
                        } catch (SocketTimeoutException s) {
                            System.out.println("Socket timed out!");
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.toString());
                }
            }

            class mythread2 extends Thread {

                IReceiver handler;
                ReceivedMessage message;

                public mythread2(IReceiver handler, ReceivedMessage message) {
                    this.handler = handler;
                    this.message = message;
                }

                public void run() {
                    handler.handleMessage(message);
                }
            }
        }
        Thread t = new mythread(ip, port, handler);
        executor.execute(t);
    }

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

    public static final String nextIpAddress(final String input) {
        final String[] tokens = input.split("\\.");
        if (tokens.length != 4) {
            throw new IllegalArgumentException();
        }
        for (int i = tokens.length - 1; i >= 0; i--) {
            final int item = Integer.parseInt(tokens[i]);
            if (item < 255) {
                tokens[i] = String.valueOf(item + 1);
                for (int j = i + 1; j < 4; j++) {
                    tokens[j] = "0";
                }
                break;
            }
        }
        return new StringBuilder()
                .append(tokens[0]).append('.')
                .append(tokens[1]).append('.')
                .append(tokens[2]).append('.')
                .append(tokens[3])
                .toString();
    }

    /*****
     * Not working
     * @return 
     */
    public String getNewIp() {
        Enumeration<NetworkInterface> nets = null;
        ArrayList<Integer> values1 = new ArrayList<Integer>();
        ArrayList<Integer> values2 = new ArrayList<Integer>();
        ArrayList<Integer> values3 = new ArrayList<Integer>();

        try {
            nets = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (NetworkInterface netint : Collections.list(nets)) {
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                String ipaddr = inetAddress.getHostAddress();
                if (ipaddr.startsWith("127")) {
                    final String[] tokens = ipaddr.split("\\.");
                    values1.add(Integer.parseInt(tokens[1]));
                    values2.add(Integer.parseInt(tokens[2]));
                    values3.add(Integer.parseInt(tokens[3]));
                }
            }
        }

        int highest1 = getHighestValue(values1);
        int highest2 = getHighestValue(values2);
        int highest3 = getHighestValue(values3);

        if(highest1 == 0 && highest2==0 && highest3<3){
            highest3=3;
        }
        String highestip= new StringBuilder()
                .append("127").append('.')
                .append(highest1).append('.')
                .append(highest2).append('.')
                .append(highest3)
                .toString();
        return nextIpAddress(highestip);
    }

    private int getHighestValue(ArrayList<Integer> array) {
        int max = array.get(0);

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) > max) {
                max = array.get(i);
            }
        }
        return max;
    }
}
