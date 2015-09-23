package me.rbrickis.chat.netty.protocol;

public abstract class PacketHandler {

    public abstract void handle(Packet packet);

}
