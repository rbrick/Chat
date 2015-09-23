package me.rbrickis.chat.protocol.packets.client;

import lombok.Getter;
import lombok.Setter;
import me.rbrickis.chat.protocol.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JoinPacket extends Packet {

    @Getter @Setter private String name;
    @Getter @Setter private String remoteAddress;
    @Getter @Setter private int remotePort;

    public JoinPacket() {
        super(0x00);
    }

    @Override
    public void read(DataInput input) {
        try {
            this.name = input.readUTF();
            this.remoteAddress = input.readUTF();
            this.remotePort = input.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutput output) {
        try {
            //            output.writeUTF(name);
            //            output.writeUTF(remoteAddress);
            //            output.writeInt(remotePort);

            byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
            output.writeInt(nameBytes.length);
            output.write(nameBytes);
            byte[] addressBytes = remoteAddress.getBytes(StandardCharsets.UTF_8);
            output.writeInt(addressBytes.length);
            output.write(addressBytes);
            output.writeInt(remotePort);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
