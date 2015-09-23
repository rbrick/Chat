package me.rbrickis.chat.client;

public class ClientTest {

    public static void main(String... args) {
        Client client = new Client("Ryan");
        client.setHandler(new ClientHandler(client));
        client.connect();
    }

}
