package client;

import server.Connection;

import java.io.IOException;
import java.net.Socket;

public class ThreadSocket extends Thread {
    private Connection connection;

    public ThreadSocket(Socket socket) throws IOException {
        this.connection = new Connection(socket);
    }

    public void run() {
        while (true) {

        }
    }
}
