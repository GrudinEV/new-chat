package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class OutputMessageHandler {
    private ObjectOutputStream out;

    public OutputMessageHandler(ObjectOutputStream out) {
        this.out = out;
    }

    public void userAdded(String userName) throws IOException {
        for (ConcurrentHashMap.Entry<String, ConnectionServer> pair : Server.getMapConnection().entrySet()) {
            if (pair.getKey().equals(userName)) {
                Message userAdded = new Message(MessageType.USER_ADDED, "Добро пожаловать в чат!");
                out.writeObject(userAdded);
            } else {
                Message userAdded = new Message(MessageType.USER_ADDED, "К чату присоединился пользователь:" + userName);
                pair.getValue().getOut().writeObject(userAdded);
            }
        }
    }

    public void activeUsers(String userName) {
        StringBuilder sb = new StringBuilder();
        for (String name : Server.getMapConnection().keySet()) {

        }
    }
}
