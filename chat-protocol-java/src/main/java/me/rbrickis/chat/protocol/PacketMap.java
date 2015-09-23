package me.rbrickis.chat.protocol;

import java.util.HashMap;
import java.util.Map;

public class PacketMap {

    private Map<Integer, Class<? extends Packet>> idToPacket;

    public PacketMap() {
        this.idToPacket = new HashMap<>();
    }


    public void register(int id, Class<? extends Packet> packet) {
        idToPacket.put(id, packet);
    }

    public Class<? extends Packet> lookupPacket(int id) {
        return idToPacket.get(id);
    }

}
