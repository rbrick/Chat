package me.rbrickis.chat.netty.protocol;


import io.netty.buffer.ByteBuf;
import lombok.Getter;

public abstract class Packet {

    @Getter private int id;

    public Packet(int id) {
        this.id = id;
    }

    public abstract void read(ByteBuf input);

    public abstract void write(ByteBuf output);
}
