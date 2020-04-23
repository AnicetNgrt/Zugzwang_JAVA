package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TUI {
    public static String choiceList(String out, String... choices) {
        while (true) {
            System.out.println(out);
            String in = readline();
            for (String choice : choices) {
                if (in.startsWith(choice)) return choice;
            }
        }
    }

    public static String readline() {
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static void println(Object o) {
        System.out.println(o);
    }
}
