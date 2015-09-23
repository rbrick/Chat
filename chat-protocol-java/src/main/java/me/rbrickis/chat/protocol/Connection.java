package me.rbrickis.chat.protocol;

public interface Connection {

    void sendPacket(Packet packet);

}
