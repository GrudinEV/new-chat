package client;

import java.io.IOException;
import java.net.Socket;

public class ThreadSocket extends Thread {
    private ConnectionClient connection;

    public ThreadSocket(Socket socket) throws IOException {
        this.connection = new ConnectionClient(socket);
    }

    public void run() {
    	connection.start();
    }
}
