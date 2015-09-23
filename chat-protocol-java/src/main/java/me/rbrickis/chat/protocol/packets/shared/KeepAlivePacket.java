package me.rbrickis.chat.protocol.packets.shared;

import me.rbrickis.chat.protocol.Packet;

import java.io.DataInput;
import java.io.DataOutput;

public class KeepAlivePacket extends Packet {

    long keepAlive;

    public KeepAlivePacket() {
        super(0x01);
    }

    @Override
    public void read(DataInput input) {
        keepAlive = System.currentTimeMillis();
    }

    @Override
    public void write(DataOutput output) {
        keepAlive = System.currentTimeMillis();
    }
}
