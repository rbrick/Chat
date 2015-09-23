package me.rbrickis.chat.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.rbrickis.chat.netty.protocol.Packet;
import me.rbrickis.chat.netty.protocol.PacketHandler;
import me.rbrickis.chat.netty.protocol.packets.client.JoinPacket;

public class Server {

    private ServerBootstrap bootstrap;
    private EventLoopGroup boss, worker;

    private int port;


    public static final PacketHandler PACKET_HANDLER = new PacketHandler() {
        @Override
        public void handle(Packet packet) {
            System.out.println("Packet Received: " + packet.toString());
            if (packet instanceof JoinPacket) {
                JoinPacket jp = (JoinPacket) packet;
                System.out
                    .println(jp.getName() + " [" + jp.getRemoteAddress() + "] has connected.");
            }
        }
    };

    public Server(int port) {
        this.port = port;
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();

        // Set up the bootstrap :D
        this.bootstrap =
            new ServerBootstrap().group(boss, worker).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        // Add handlers into the pipeline here.
                        channel.pipeline().addLast("encoder", new PacketEncoder());
                        channel.pipeline().addLast("decoder", new PacketDecoder());
                        channel.pipeline().addLast("handler", new ClientConnectionHandler());
                    }
                }).childOption(ChannelOption.SO_KEEPALIVE, true);
    }


    public void start() {
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        worker.shutdownGracefully();
        boss.shutdownGracefully();
    }
}
