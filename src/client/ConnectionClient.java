package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.ConsoleHandler;
import server.Message;
import server.MessageType;

public class ConnectionClient extends Thread {
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ConnectionClient(Socket socket) throws IOException {
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    /*Приветствие нового пользователя.
    * 1. Получение имени пользователя.
    * 2. Добавление записи о новом пользователе и соединении с ним в Server.mapConnection
    * 3. Если введено некорректное имя пользователя, то запрос имени повторяется.*/
    
    public void welcomeAndAddUser() throws IOException, ClassNotFoundException {
    	Message hi = (Message) in.readObject();
    	ConsoleHandler.writeString(hi.getData());
    	
    	String name = ConsoleHandler.readLine();
    	Message nameMsg = new Message(MessageType.USER_NAME, name);
    	out.writeObject(nameMsg);
    	
    	hi = (Message) in.readObject();
    	ConsoleHandler.writeString(hi.getData());
    	
//        OutputMessageHandler omh = new OutputMessageHandler(out);
//        Message hi = new Message(MessageType.USER_NAME_REQUEST, "Добро пожаловать в чат, представьтесь пожалуйста.");
//        out.writeObject(hi);
//        boolean nameAccepted = false;
//        String userName = null;
//        while (!nameAccepted) {
//            Message mesUserName = (Message) in.readObject();
//            if (mesUserName.getType().equals(MessageType.USER_NAME)) {
//                userName = mesUserName.getData();
//                if (userName != null && userName.matches("[a-zA-Z]\\w*\\s?\\w*") && userName.length() > 3 && userName.length() < 21) {
//                    Server.addConnection(userName, this);
//                    omh.userAdded(userName);
//                }
//            } else {
//                Message err = new Message(MessageType.USER_NAME_REQUEST, "Что-то пошло не так. Ваше имя не принято. Представьтесь повторно пожалуйста.");
//                out.writeObject(err);
//            }
//        }
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
