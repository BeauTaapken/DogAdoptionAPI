package beau.taapken.dogadoption.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class EnumUtils {
    private static final Gson gson = new Gson();
    private final JsonObject json =new JsonObject();

    public static <T extends Enum<T>> String getStringValues(Class<T> enumClass) {
        HashMap<String,Object> map = new HashMap();
        map.put("breeds", getStringValuesWithStringExtractor(enumClass, Enum::name));
        return gson.toJson(map);
    }

    public static <T extends Enum<T>> String[] getStringValuesWithStringExtractor(
            Class<T> enumClass,
            Function<? super T, String> extractor
    ) {
        return Stream.of(enumClass.getEnumConstants()).map(extractor).toArray(String[]::new);
    }
}
