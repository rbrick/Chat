package me.rbrickis.chat.protocol;


import me.rbrickis.chat.protocol.packets.client.JoinPacket;
import me.rbrickis.chat.protocol.packets.server.ResponsePacket;
import me.rbrickis.chat.protocol.packets.shared.KeepAlivePacket;

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
        registerClient(KeepAlivePacket.class, 0x01);



        registerServer(ResponsePacket.class, 0x00);
        registerServer(KeepAlivePacket.class, 0x01);

    }

}
