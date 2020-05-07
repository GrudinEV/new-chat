package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHandler {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeString(String line) {
        System.out.println(line);
    }

    public static String readLine() {
        String str = null;
        try {
            str = reader.readLine();
        } catch (IOException e) {
            writeString("Произошла ошибка ввода-вывода.");
            readLine();
        }
        return str;
    }

    public static int readInt() {
        int number = 0;
        try {
            number = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            writeString("Вы ввели не целое число. Прошу повторить ввод.");
            readInt();
        } catch (IOException e) {
            writeString("Произошла ошибка ввода-вывода.");
            e.printStackTrace();
            readInt();
        }
        return number;
    }
}
