package clients;

import socket_interaction.SocketManager;

public abstract class Client {
    protected SocketManager socket_manager;

    public Client(String address, int port) {
        socket_manager = new SocketManager(address, port);
    }
    public void run() throws Exception {
        socket_manager.run();
    }
    public void stop() throws Exception {
        socket_manager.stop();
    }
    public void refresh() throws Exception {
        stop();
        run();
    }
}
