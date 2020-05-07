package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Server {
    private static boolean serverConnected;
    private static ConcurrentMap<String, Connection> mapConnection = new ConcurrentHashMap<>();

    protected static boolean isServerConnected() {
        return serverConnected;
    }

    protected static void setServerConnected(boolean serverConnected) {
        Server.serverConnected = serverConnected;
    }

    public static ConcurrentMap<String, Connection> getMapConnection() {
        return mapConnection;
    }

    public static void addConnection(String userName, Connection connection) {
        mapConnection.put(userName, connection);
    }
    public static void remove(String userName) {
        mapConnection.remove(userName);
    }

    public static void main(String[] args) {
        final int portMin = 1_500;
        final int portMax = 60_000;
        ConsoleHandler.writeString(String.format("Введите порт сервера (целое число в диапазоне от %d до %d включительно)" , portMin, portMax));
        boolean b = true;
        int port = 0;
        while (b) {
            port = ConsoleHandler.readInt();
            if (port >= portMin && port <= portMax) {
                b = false;
            } else {
                ConsoleHandler.writeString(String.format("Вы ввели целое число, не соответствующее указанному условию (целое число в диапазоне от %d до %d включительно). Введите порт сервера повторно." , portMin, portMax));
            }
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            ConsoleHandler.writeString("Что-то пошло не так, не удалось запустить сервер.");
        }
        serverConnected = true;
        ConsoleHandler.writeString("Сервер запущен. Ожидаю создание подключения");
        while (serverConnected) {
            try {
                Socket socket = serverSocket.accept();
                new Connection(socket).start();
                ConsoleHandler.writeString("Создано новое подключение:" + socket.getRemoteSocketAddress());
            } catch (IOException e) {
                ConsoleHandler.writeString("Произошла ошибка ввода-вывода при создании подключения");
            }
        }
    }
}
