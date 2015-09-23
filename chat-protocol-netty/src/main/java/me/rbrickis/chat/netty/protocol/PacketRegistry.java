package me.rbrickis.chat.netty.protocol;



import me.rbrickis.chat.netty.protocol.packets.client.JoinPacket;
import me.rbrickis.chat.netty.protocol.packets.server.ResponsePacket;

/**
 * A repository for Packets
 */
public final class PacketRegistry {


    public static final PacketMap CLIENT_PACKETS = new PacketMap();
    public static final PacketMap SERVER_PACKETS = new PacketMap();



    public static void registerClient(Class<? extends Packet> packet, int id) {
        CLIENT_PACKETS.register(id, packet);
    }

    public static void registerServer(Class<? extends Packet> packet, int id) {
        SERVER_PACKETS.register(id, packet);
    }



    static {
        registerClient(JoinPacket.class, 0x00);

        registerServer(ResponsePacket.class, 0x00);

    }

}
