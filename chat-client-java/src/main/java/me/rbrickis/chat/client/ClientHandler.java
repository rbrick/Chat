package me.rbrickis.chat.client;

import me.rbrickis.chat.protocol.Connection;
import me.rbrickis.chat.protocol.Packet;
import me.rbrickis.chat.protocol.PacketHandler;
import me.rbrickis.chat.protocol.packets.server.ResponsePacket;

/**
 * Listens to incoming messages from the server
 */
public class ClientHandler extends PacketHandler {

    private Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public void handle(Packet packet, Connection connection) {
        if (packet instanceof ResponsePacket) {
            System.out.println(((ResponsePacket) packet).getMessage());
        }
    }
}
