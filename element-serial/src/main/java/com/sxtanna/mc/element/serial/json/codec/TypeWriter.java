package com.sxtanna.mc.element.serial.json.codec;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.function.Function;

public interface TypeWriter<T> extends JsonSerializer<T>
{

    @FunctionalInterface
    interface Raw<T> extends TypeWriter<T>, Function<T, JsonElement>
    {

        @Override
        @NotNull JsonElement push(@Nullable final T data);


        @Override
        default @NotNull JsonElement apply(@Nullable final T data)
        {
            return push(data);
        }

    }


    default @NotNull JsonElement push(@Nullable final T data)
    {
        return JsonNull.INSTANCE;
    }

    default @NotNull JsonElement push(@Nullable final T data, @NotNull final Type type)
    {
        return push(data);
    }

    default @NotNull JsonElement push(@Nullable final T data, @NotNull final Type type, @NotNull final JsonSerializationContext context)
    {
        return push(data, type);
    }


    @Override
    default JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context)
    {
        return push(src, typeOfSrc, context);
    }

}