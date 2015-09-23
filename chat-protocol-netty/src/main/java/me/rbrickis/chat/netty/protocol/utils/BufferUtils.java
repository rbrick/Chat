package me.rbrickis.chat.netty.protocol.utils;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public final class BufferUtils {

    private BufferUtils() {
    }


    public static void writeUTF(ByteBuf buf, String string) {
        byte[] stringAsByteArray = string.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(stringAsByteArray.length);
        buf.writeBytes(stringAsByteArray);
    }

    public static String readUTF(ByteBuf buf) {
        int length = buf.readInt();
        byte[] data = new byte[length];
        buf.readBytes(data);
        return new String(data, StandardCharsets.UTF_8);
    }



}
