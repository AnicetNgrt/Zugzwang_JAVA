package Utils;

import Communication.CmdTypes;
import Communication.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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

    public static Command promptCmd(String in) {
        String[] parts = in.split("\\s+");
        if (parts.length <= 0) {
            println("Write something at least");
            return null;
        }
        String name = parts[0];
        String[] args = parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0];
        CmdTypes type = CmdTypes.nameToType(name);
        if (type == null) {
            println("Unknown command");
            return null;
        }
        Command cmd = new Command(type);
        for (int i = 0; i < type.getStrCount(); i++) {
            if (i >= args.length) {
                println("Missing string args");
                return null;
            }
            cmd.set(type.getParams()[i], args[i]);
        }
        for (int i = type.getStrCount(); i < type.getParamsCount(); i++) {
            if (i >= args.length) {
                println("Missing int args");
                return null;
            }
            cmd.set(type.getParams()[i], args[i]);
        }
        return cmd;
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static void println(Object o) {
        System.out.println(o);
    }
}
