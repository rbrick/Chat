package me.rbrickis.chat.protocol;

public abstract class PacketHandler {

    public abstract void handle(Packet packet, Connection connection);

}
