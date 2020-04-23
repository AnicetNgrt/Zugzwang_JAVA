package Utils;

public class TUI {
    public static String choiceList(String out, String... choices) {
        while (true) {
            System.out.println(out);
            String in = System.console().readLine();
            for (String choice : choices) {
                if (in.startsWith(choice)) return choice;
            }
        }
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static void println(Object o) {
        System.out.println(o);
    }
}
