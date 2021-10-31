package com.sxtanna.mc.element.serial.json.codec;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.function.Function;

public interface TypeReader<T> extends JsonDeserializer<T>
{

    @FunctionalInterface
    interface Raw<T> extends TypeReader<T>, Function<JsonElement, T>
    {

        @Override
        @Nullable T pull(@NotNull final JsonElement json);


        @Override
        default @Nullable T apply(@NotNull final JsonElement json)
        {
            return pull(json);
        }

    }


    default @Nullable T pull(@NotNull final JsonElement json)
    {
        return null;
    }

    default @Nullable T pull(@NotNull final JsonElement json, @NotNull final Type type)
    {
        return pull(json);
    }

    default @Nullable T pull(@NotNull final JsonElement json, @NotNull final Type type, @NotNull final JsonDeserializationContext context)
    {
        return pull(json, type);
    }


    @Override
    default T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return pull(json != null ? json : JsonNull.INSTANCE, typeOfT, context);
    }

}