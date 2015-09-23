package me.rbrickis.chat.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import me.rbrickis.chat.netty.protocol.Packet;
import me.rbrickis.chat.netty.protocol.PacketRegistry;

import java.util.List;

/**
 * This class decodes a ByteBuf to a Packet
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() > 0) { // Only read if there are readable bytes.
            final int packetId = in.readInt();

            // System.out.println(packetId);

            // We would be decoding packets sent from the client
            Class<? extends Packet> packetClass =
                PacketRegistry.CLIENT_PACKETS.lookupPacket(packetId);

            if (packetClass != null) {
                try {
                    Packet packet = packetClass.newInstance();
                    packet.read(in); // Read the packet.
                    out.add(packet); // And that is how we decode a packet :+1:
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            } else {
                throw new DecoderException("Could not find packet for id " + packetId);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out
            .println("Error [" + cause.getClass().getSimpleName() + "] " + cause.getMessage());
    }
}
