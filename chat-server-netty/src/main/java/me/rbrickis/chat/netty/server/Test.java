package me.rbrickis.chat.netty.server;

public class Test {

    public static void main(String... args) {
        Server server = new Server(1337);
        server.start();
    }
}
