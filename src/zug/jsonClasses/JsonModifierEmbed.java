package zug.jsonClasses;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JsonModifierEmbed implements Jsonable {
    public String modifierName;
    public int[] playersI;
    public int[][] pawnsI;
    public String orientationName;

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return writable.toString();
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("modifierName", modifierName);
        json.put("playersI", playersI);
        json.put("pawnsI", pawnsI);
        json.put("orientationName", orientationName);
        json.toJson(writer);
    }
}
