package client;

import server.ConsoleHandler;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private static Socket socket;

    public static void main(String[] args) {
        String serverAddress = null;
        final int portMin = 1_500;
        final int portMax = 60_000;
        boolean b1 = true;
        while (b1) {
            ConsoleHandler.writeString("Введите адрес сервера");
            serverAddress = ConsoleHandler.readLine();
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
            try {
                Socket socket = new Socket(serverAddress, port);
                b1 = false;
            } catch (IOException e) {
                ConsoleHandler.writeString("Не удалось создать подключение к серверу. Попробуйте ещё раз.");
            }
        }
        ConsoleHandler.writeString("Подключение выполнено!");
        new ThreadSocket(socket).start();
    }
}
