package utils;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtils<JSONCLASS> {

    final Class<JSONCLASS> typeParameterClass;

    public JsonUtils(Class<JSONCLASS> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public JSONCLASS readJson(String path) {
        try (FileReader fileReader = new FileReader(("C:\\projects\\user3.json"))) {
            JsonObject deserialize = (JsonObject) Jsoner.deserialize(fileReader);
            Mapper mapper = new DozerBeanMapper();
            return mapper.map(deserialize, typeParameterClass);

        } catch (IOException | JsonException e) {
            e.printStackTrace();
            return null;
        }
    }
}