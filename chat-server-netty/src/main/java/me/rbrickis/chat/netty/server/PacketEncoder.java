package me.rbrickis.chat.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.rbrickis.chat.netty.protocol.Packet;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    /**
     * Encodes a packet into a ByteBuf, should be passed through when calling
     * Channel#write
     * Channel#writeAndFlush
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) throws Exception {
        out.writeInt(in.getId()); // Write packet id
        in.write(out); // Encode the packet.
    }
}
