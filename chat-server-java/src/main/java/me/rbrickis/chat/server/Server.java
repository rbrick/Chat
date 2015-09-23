package me.rbrickis.chat.server;

import me.rbrickis.chat.protocol.Connection;
import me.rbrickis.chat.protocol.Packet;
import me.rbrickis.chat.protocol.PacketHandler;
import me.rbrickis.chat.protocol.PacketRegistry;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket socket;

    private List<ClientConnection> connections = new ArrayList<>();

    private PacketHandler handler;


    public Server(int port) {
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHandler(PacketHandler handler) {
        this.handler = handler;
    }

    public void start() {
        new Thread("Server thread") {
            @Override
            public void run() {
                while (socket.isBound()) {
                    try {
                        connections.add(new ClientConnection(socket.accept()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void broadcastPacket(Packet packet) {
        for (ClientConnection connection : connections) {
            connection.sendPacket(packet);
        }
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public class ClientConnection implements Connection {

        private Socket socket;

        private DataOutput output;
        private DataInput input;

        public ClientConnection(final Socket socket) {
            this.socket = socket;

            try {
                this.output = new DataOutputStream(socket.getOutputStream());
                this.input = new DataInputStream(socket.getInputStream());
                socket.setKeepAlive(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread("Server-Client-Connection-" + socket.toString()) {
                @Override
                public void run() {
                    while (true) {
                        Packet packet = readPacket();
                        if (packet != null) {
                            handler.handle(packet, ClientConnection.this);
                        }
                    }
                }
            }.start();

        }

        public Packet readPacket() {
            try {
                int packetId = input.readInt();
                Class<? extends Packet> clazz =
                    PacketRegistry.CLIENT_PACKETS.lookupPacket(packetId);
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

        @Override
        public void sendPacket(Packet packet) {
            try {
                output.writeInt(packet.getId());
                packet.write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
