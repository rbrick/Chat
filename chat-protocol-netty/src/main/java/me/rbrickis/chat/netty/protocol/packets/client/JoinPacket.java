package me.rbrickis.chat.netty.protocol.packets.client;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import me.rbrickis.chat.netty.protocol.Packet;
import me.rbrickis.chat.netty.protocol.utils.BufferUtils;

public class JoinPacket extends Packet {

    @Getter @Setter private String name;
    @Getter @Setter private String remoteAddress;
    @Getter @Setter private int remotePort;

    public JoinPacket() {
        super(0x00);
    }

    @Override
    public void read(ByteBuf input) {
        this.name = BufferUtils.readUTF(input);
        this.remoteAddress = BufferUtils.readUTF(input);
        this.remotePort = input.readInt();
    }

    @Override
    public void write(ByteBuf output) {
        BufferUtils.writeUTF(output, name);
        BufferUtils.writeUTF(output, remoteAddress);
        output.writeInt(remotePort);
    }



}
