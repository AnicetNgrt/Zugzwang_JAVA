package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

    public static Object readJson(String path, final Class<?> c) {
        try {
            System.out.println(path);
            System.out.println(c);
            String text = Files.readString(Paths.get(path));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(text, c);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeJson(String path, Object normObj) {
        if (path.endsWith("\\")) path = path.substring(0, path.length() - 2);
        if (!path.endsWith(".json")) path += ".json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(normObj);

        System.out.println(json);

        // Java objects to JSON file
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}