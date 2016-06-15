package bom.dom.com.recipe.rest;

/**
 * Created by Админ on 13.06.2016.
 */

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by anton on 06.05.16.
 */
public class TypeFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {

                JsonElement jsonElement = elementAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    jsonElement = jsonObject;

                    if (jsonObject.has("recipes") && jsonObject.get("recipes").isJsonArray()) {
                        jsonElement = jsonObject.get("recipes");
                    }

                }

                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}
