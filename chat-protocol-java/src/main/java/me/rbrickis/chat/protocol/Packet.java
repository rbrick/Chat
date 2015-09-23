package me.rbrickis.chat.protocol;


import lombok.Getter;

import java.io.DataInput;
import java.io.DataOutput;

public abstract class Packet {

    @Getter private int id;

    public Packet(int id) {
        this.id = id;
    }

    public abstract void read(DataInput input);

    public abstract void write(DataOutput output);
}
