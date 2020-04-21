package zug.jsonClasses;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JsonPawn implements Jsonable {
    public int x;
    public int y;
    public boolean exiled;
    public boolean alive;
    public boolean immortal;

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
        json.put("x", x);
        json.put("y", y);
        json.put("alive", alive);
        json.put("exiled", exiled);
        json.put("immortal", immortal);
        json.toJson(writer);
    }
}
