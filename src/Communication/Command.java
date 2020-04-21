package Communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class Command {
    public CmdTypes type;
    public HashMap<String, String> strParams;
    public HashMap<String, Integer> intParams;

    public Command(CmdTypes type) {
        this.type = type;

        strParams = new HashMap<>();
        for (int i = 0; i < type.getStrCount(); i++)
            strParams.put(type.getParams()[i], null);

        intParams = new HashMap<>();
        for (int i = type.getStrCount(); i < type.getParams().length; i++)
            intParams.put(type.getParams()[i], null);
    }

    public static Command fromJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, Command.class);
    }

    public boolean isMissing() {
        boolean is = false;
        for (String key : strParams.keySet())
            if (strParams.get(key) == null) is = true;
        for (String key : intParams.keySet())
            if (intParams.get(key) == null) is = true;
        return is;
    }

    public void set(String key, Object value) {
        if (strParams.containsKey(key)) {
            String v = (String) value;
            strParams.replace(key, v);
        }
        if (intParams.containsKey(key)) {
            int v = (int) value;
            intParams.replace(key, v);
        }
    }

    public int getInt(String key) {
        return intParams.get(key);
    }

    public String getStr(String key) {
        return strParams.get(key);
    }

    public boolean isNull() {
        return type == null || strParams == null || intParams == null;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        if (type == null) return "null command";
        String str = type.name();
        for (String key : strParams.keySet())
            str += " " + key + ": " + strParams.get(key);
        for (String key : intParams.keySet())
            str += " " + key + ": " + intParams.get(key);
        return str;
    }
}
