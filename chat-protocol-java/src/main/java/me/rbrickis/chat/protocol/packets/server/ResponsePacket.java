package me.rbrickis.chat.protocol.packets.server;

import lombok.Getter;
import lombok.Setter;
import me.rbrickis.chat.protocol.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ResponsePacket extends Packet {

    @Getter @Setter private String message;

    public ResponsePacket() {
        super(0x00);
    }

    @Override
    public void read(DataInput input) {
        try {
            this.message = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutput output) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
