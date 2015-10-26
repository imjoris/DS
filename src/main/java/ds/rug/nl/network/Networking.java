package ds.rug.nl.network;

import ds.rug.nl.main.Config;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.threads.IReceiver;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    class mythread2 extends Thread {

        IReceiver handler;
        DTO dto;

        public mythread2(IReceiver handler, DTO dto) {
            this.handler = handler;
            this.dto = dto;
        }

        
        @Override
        public void run() {
            handler.handleDTO(dto);
        }
    }

    public void send(DTO dto, InetAddress address, int port) {
        Socket socket = null;
        ObjectOutputStream out = null;
        try {
            //socket = new Socket(port);
            socket = new Socket(address, port);
            
//            InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
//            s.bind(socketAddress);
//            s.setReuseAddress(true);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(dto);
            out.flush();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startReceiving(hostInfo host) {
        class mythread extends Thread {

            hostInfo host;

            public mythread(hostInfo host) {
                this.host = host;
            }

            @Override
            public void run() {
                try {
                    ServerSocket serversock = new ServerSocket();//Config.commandPort, 50, InetAddress.getByName(ip));
                    serversock.setReuseAddress(false);

                    serversock.bind(new InetSocketAddress(host.ip, Config.commandPort));
                    System.out.println(host.hostname + " is waiting for clients on " + serversock.getInetAddress().getHostName() + " port "
                            + serversock.getLocalPort() + "...");

                    ExecutorService executor = Executors.newFixedThreadPool(5);

                    while (true) {
                        try {
                            Socket callsocket = serversock.accept();
                            System.out.println(host.hostname + "accepted incomming connection from "
                                    + callsocket.getRemoteSocketAddress());

                            ObjectInputStream in = new ObjectInputStream(callsocket.getInputStream());
                            DTO objectReceived = (DTO) in.readObject();
                            Thread handleThreat = new mythread2(host.handler, objectReceived);
                            executor.execute(handleThreat);

                        } catch (SocketTimeoutException s) {
                            System.out.println(host.hostname + " socket timed out!");
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.toString());
                }
            }

            class mythread2 extends Thread {

                public IReceiver handler;
                public DTO dto;

                public mythread2(IReceiver handler, DTO message) {
                    this.handler = handler;
                    this.dto = message;
                }

                @Override
                public void run() {
                    handler.handleDTO(dto);
                }
            }
        }

        //String hostname, String ip, int port, IReceiver handler
        Thread t = new mythread(host);
        executor.execute(t);
    }

    private byte[] getBytesOfObject(Object o) {
        ObjectOutputStream os = null;
        byte[] sendBuf = null;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
            os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(o);
            os.flush();
            //retrieves byte array
            sendBuf = byteStream.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sendBuf;
    }

    public void sendMulticast(DTO data) {
        byte[] sendData = getBytesOfObject(data);
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

    private void joinMulticastGroup(String ip, int port) {
        try {
            MulticastSocket socket;
            socket = new MulticastSocket(Config.multicastPort); // must bind receive side
            socket.joinGroup(InetAddress.getByName(Config.multicastAdres));
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startReceiveMulticasts(hostInfo hostinfo, IReceiver handler) {
        
        class mythread extends Thread {

            IReceiver handler;
            InetAddress ip;
            int port;

            public mythread(InetAddress ip, int port, IReceiver handler) {
                this.handler = handler;
                this.ip = ip;
                this.port = port;
            }

            public void run() {

                try {
                    byte[] b = new byte[Config.bufferSize];
                    DatagramPacket dgram = new DatagramPacket(b, b.length);
                    MulticastSocket socket;
                    socket = new MulticastSocket(Config.multicastPort); // must bind receive side
                    socket.joinGroup(InetAddress.getByName(Config.multicastAdres));

                    ExecutorService executor = Executors.newFixedThreadPool(5);

                    while (true) {
                        try {
                            socket.receive(dgram); // blocks until a datagram is received

                            System.err.println("Received " + dgram.getLength()
                                    + " bytes from " + dgram.getAddress());
                            dgram.setLength(b.length); // must reset length field!
                            int len = 0;
//      // byte[] -> int
//      for (int i = 0; i < 4; ++i) {
//          len |= (data[3-i] & 0xff) << (i << 3);
//      }
//
//      // now we know the length of the payload
//      byte[] buffer = new byte[len];
//      packet = new DatagramPacket(buffer, buffer.length );
//      socket.receive(packet);

                            ByteArrayInputStream baos = new ByteArrayInputStream(b);
                            ObjectInputStream ois = new ObjectInputStream(baos);
                            DTO dto = (DTO) ois.readObject();

                            Thread handlerThread = new mythread2(handler, dto);
                            executor.execute(handlerThread);
                        } catch (IOException ex) {
                            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        Thread multicastListenThread = new mythread(hostinfo.ip, hostinfo.port, hostinfo.handler);
        executor.execute(multicastListenThread);
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

    /**
     * ***
     * Not working
     *
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

        if (highest1 == 0 && highest2 == 0 && highest3 < 3) {
            highest3 = 3;
        }
        String highestip = new StringBuilder()
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
