package assets;

import zug.classes.Game;

import java.io.File;
import java.io.IOException;

public class AssetsUtils {

    public static void gameToJson(Game g) {
        try {
            String canonicalPath = new File(".").getCanonicalPath();


        } catch (IOException e) {
            System.out.println("IOException Occured" + e.getMessage());
        }
    }
}
