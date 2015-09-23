package me.rbrickis.chat.client;

import me.rbrickis.chat.protocol.Connection;
import me.rbrickis.chat.protocol.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Socket;

public class RemoteConnection implements Connection {

    private DataInput input; // Don't really need this
    private DataOutput output;
    private Socket socket; // Don't really need this

    public RemoteConnection(DataInput input, DataOutput output, Socket socket) {
        this.input = input;
        this.output = output;
        this.socket = socket;
    }

    @Override
    public void sendPacket(Packet packet) {
        try {
            output.writeInt(packet.getId());
            packet.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
