package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void welcomeAndAddUser() throws IOException, ClassNotFoundException {
        OutputMessageHandler omh = new OutputMessageHandler(out);
        Message hi = new Message(MessageType.USER_NAME_REQUEST, "Добро пожаловать в чат, представьтесь пожалуйста.");
        out.writeObject(hi);
        boolean nameAccepted = false;
        String userName = null;
        while (!nameAccepted) {
            Message mesUserName = (Message) in.readObject();
            if (mesUserName.getType().equals(MessageType.USER_NAME)) {
                userName = mesUserName.getData();
                if (userName != null && userName.matches("[a-zA-Z]\\w*\\s?\\w*") && userName.length() > 3 && userName.length() < 21) {
                    Server.addConnection(userName, this);
                    omh.userAdded(userName);
                }
            } else {
                Message err = new Message(MessageType.USER_NAME_REQUEST, "Что-то пошло не так. Ваше имя не принято. Представьтесь повторно пожалуйста.");
                out.writeObject(err);
            }
        }
    }

    public void run() {
        try {
            welcomeAndAddUser();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
