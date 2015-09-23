package me.rbrickis.chat.server;

import me.rbrickis.chat.protocol.Connection;
import me.rbrickis.chat.protocol.Packet;
import me.rbrickis.chat.protocol.PacketHandler;
import me.rbrickis.chat.protocol.packets.client.JoinPacket;


/**
 * Listens to incoming packets from the client
 */
public class ServerHandler extends PacketHandler {

    private Server server;

    public ServerHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handle(Packet packet, Connection connection) {
        System.out.println("Packet Received: " + packet.toString());
        if (packet instanceof JoinPacket) {
            JoinPacket jp = (JoinPacket) packet;
            System.out.println(jp.getName() + " [" + jp.getRemoteAddress() + "] has connected.");
        }
    }
}
