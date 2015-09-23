package me.rbrickis.chat.netty.protocol.packets.server;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import me.rbrickis.chat.netty.protocol.Packet;
import me.rbrickis.chat.netty.protocol.utils.BufferUtils;

public class ResponsePacket extends Packet {

    @Getter @Setter private String message;

    public ResponsePacket() {
        super(0x00);
    }

    @Override
    public void read(ByteBuf input) {
        this.message = BufferUtils.readUTF(input);
    }

    @Override
    public void write(ByteBuf output) {
        BufferUtils.writeUTF(output, message);
    }
}
