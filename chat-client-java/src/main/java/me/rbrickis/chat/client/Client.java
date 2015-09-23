package me.rbrickis.chat.client;

import me.rbrickis.chat.protocol.Packet;
import me.rbrickis.chat.protocol.PacketHandler;
import me.rbrickis.chat.protocol.PacketRegistry;
import me.rbrickis.chat.protocol.packets.client.JoinPacket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    private String name;
    private Socket socket;

    private DataInput input;
    private DataOutput output;

    private PacketHandler handler;

    private RemoteConnection serverConnection;

    public Client(String name) {
        this.name = name;
        this.socket = new Socket();
    }

    public void setHandler(PacketHandler handler) {
        this.handler = handler;
    }

    public void connect() {
        try {
            socket.connect(new InetSocketAddress("localhost", 1337));
            socket.setKeepAlive(true);
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());
            this.serverConnection =
                new RemoteConnection(input, output, socket); // Initialize the server connection
            JoinPacket packet = new JoinPacket();

            packet.setName(name);
            packet.setRemoteAddress(socket.getLocalAddress().getHostAddress());
            packet.setRemotePort(socket.getLocalPort());

            sendPacket(packet);

            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startListening() {
        new Thread("Client-" + name + "-Listener") {
            @Override
            public void run() {
                while (true) {
                    Packet packet = readPacket();
                    if (packet != null) {
                        handler.handle(packet, serverConnection);
                    }
                }
            }
        }.start();
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public Packet readPacket() {
        try {
            int packetId = input.readInt();
            Class<? extends Packet> clazz = PacketRegistry.SERVER_PACKETS.lookupPacket(packetId);
            if (clazz != null) {
                Packet packet = clazz.newInstance();
                packet.read(input);
                return packet;
            }
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendPacket(Packet packet) {
        try {
            output.writeInt(packet.getId());
            packet.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
