package entites;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FieldsDeserilizer implements JsonDeserializer<Result> {

    @Override
    public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Result options = new Gson().fromJson(json, Result.class);
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("media")) {
            JsonElement elem = jsonObject.get("media");
            if (elem != null && !elem.isJsonNull()) {
                String valuesString = elem.toString();
                if (valuesString.length() > 2) {
                    System.out.println("--value-" + valuesString);
                    List<Medium> values = new Gson().fromJson(valuesString, new TypeToken<ArrayList<Medium>>() {
                    }.getType());
                     options.setMedia(values);
                }
            }
        }
        return options ;
    }
}
