package me.rbrickis.chat.netty.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import me.rbrickis.chat.netty.protocol.Packet;

/**
 * A client connection
 */
public class ClientConnectionHandler extends ChannelHandlerAdapter {

    /**
     * When a client connects
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client disconnecting");
    }

    /**
     * After a packet passes through our decoder, channelRead gets called
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;
        Server.PACKET_HANDLER.handle(packet);
    }
}
