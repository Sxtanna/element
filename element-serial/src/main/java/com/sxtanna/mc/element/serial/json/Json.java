package com.sxtanna.mc.element.serial.json;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.sxtanna.mc.element.serial.json.build.JsonArrBuilder;
import com.sxtanna.mc.element.serial.json.build.JsonObjBuilder;
import com.sxtanna.mc.element.serial.json.codec.TypeReader;
import com.sxtanna.mc.element.serial.json.codec.TypeWriter;
import com.sxtanna.mc.json.JsonMap;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public enum Json
{
    ;


    @NotNull
    private static final Map<Type, Object> ADAPTERS = new HashMap<>();


    @NotNull
    private static final JsonParser            PARSER = new JsonParser();
    @NotNull
    private static final AtomicReference<Gson> NORMAL = new AtomicReference<>();
    @NotNull
    private static final AtomicReference<Gson> PRETTY = new AtomicReference<>();


    static
    {
        refreshGsonInstances();
    }


    private static void refreshGsonInstances()
    {
        final var builder = builder();

        NORMAL.set(builder.create());
        PRETTY.set(builder.setPrettyPrinting().create());

        JsonMap.FALLBACK_GSON_REF.set(NORMAL.get());
    }


    public static @NotNull Gson normal()
    {
        return NORMAL.get();
    }

    public static @NotNull Gson pretty()
    {
        return PRETTY.get();
    }


    public static @NotNull GsonBuilder builder()
    {
        final var builder = new GsonBuilder().disableHtmlEscaping()
                                             .enableComplexMapKeySerialization()
                                             .serializeSpecialFloatingPointValues();

        ADAPTERS.forEach(builder::registerTypeAdapter);

        return builder;
    }


    public static @NotNull String encodeToText(@Nullable final Object value)
    {
        return normal().toJson(value);
    }

    public static @NotNull String encodeToText(@Nullable final Object value, @NotNull final Class<?> clazz)
    {
        return normal().toJson(value, clazz);
    }


    public static @NotNull JsonElement encodeToJson(@Nullable final Object value)
    {
        return normal().toJsonTree(value);
    }

    public static @NotNull JsonElement encodeToJson(@Nullable final Object value, @NotNull final Class<?> clazz)
    {
        return normal().toJsonTree(value, clazz);
    }


    public static <T> @Nullable T decode(@NotNull final Class<T> clazz, @Nullable final String json)
    {
        return normal().fromJson(json, clazz);
    }

    public static <T> @Nullable T decode(@NotNull final Class<T> clazz, @Nullable final JsonElement json)
    {
        return normal().fromJson(json, clazz);
    }

    public static <T> @Nullable T decode(@NotNull final TypeToken<T> token, @Nullable final String json)
    {
        return normal().fromJson(json, token.getType());
    }

    public static <T> @Nullable T decode(@NotNull final TypeToken<T> token, @Nullable final JsonElement json)
    {
        return normal().fromJson(json, token.getType());
    }


    public static @NotNull JsonElement parse(@NotNull final String json)
    {
        return PARSER.parse(json);
    }

    public static @NotNull JsonElement parse(@NotNull final Reader json)
    {
        return PARSER.parse(json);
    }

    public static @NotNull JsonElement parse(@NotNull final InputStream json)
    {
        return parse(new BufferedReader(new InputStreamReader(json)));
    }


    @Contract(" -> new")
    public static @NotNull JsonArrBuilder buildArr()
    {
        return new JsonArrBuilder();
    }

    @Contract(" -> new")
    public static @NotNull JsonObjBuilder buildObj()
    {
        return new JsonObjBuilder();
    }

    @Contract("_ -> new")
    public static @NotNull JsonPrimitive primitive(@NotNull final String data)
    {
        return new JsonPrimitive(data);
    }

    @Contract("_ -> new")
    public static @NotNull JsonPrimitive primitive(@NotNull final Number data)
    {
        return new JsonPrimitive(data);
    }

    @Contract("_ -> new")
    public static @NotNull JsonPrimitive primitive(@NotNull final Boolean data)
    {
        return new JsonPrimitive(data);
    }


    public static <T> void reader(@NotNull final Class<T> clazz, @NotNull final TypeReader<T> reader)
    {
        reader(clazz, true, reader);
    }

    public static <T> void reader(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final TypeReader<T> reader)
    {
        ADAPTERS.put(clazz, reader);

        if (refresh)
        {
            refreshGsonInstances();
        }
    }


    public static <T> void writer(@NotNull final Class<T> clazz, @NotNull final TypeWriter<T> writer)
    {
        writer(clazz, true, writer);
    }

    public static <T> void writer(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final TypeWriter<T> writer)
    {
        ADAPTERS.put(clazz, writer);

        if (refresh)
        {
            refreshGsonInstances();
        }
    }


    public static <T> void reader(@NotNull final Class<T> clazz, @NotNull final TypeReader.Raw<T> reader)
    {
        reader(clazz, true, reader);
    }

    public static <T> void reader(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final TypeReader.Raw<T> reader)
    {
        reader(clazz, refresh, ((TypeReader<T>) reader));
    }


    public static <T> void writer(@NotNull final Class<T> clazz, @NotNull final TypeWriter.Raw<T> writer)
    {
        writer(clazz, true, writer);
    }

    public static <T> void writer(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final TypeWriter.Raw<T> writer)
    {
        writer(clazz, refresh, ((TypeWriter<T>) writer));
    }


    public static <T, C extends TypeReader<T> & TypeWriter<T>> void register(@NotNull final Class<T> clazz, @NotNull final C codec)
    {
        register(clazz, true, codec);
    }

    public static <T, C extends TypeReader<T> & TypeWriter<T>> void register(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final C codec)
    {
        ADAPTERS.put(clazz, codec);

        if (refresh)
        {
            refreshGsonInstances();
        }
    }


    public static <T> void register(@NotNull final Class<T> clazz, @NotNull final TypeReader<T> reader, @NotNull final TypeWriter<T> writer)
    {
        register(clazz, true, reader, writer);
    }

    public static <T> void register(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final TypeReader<T> reader, @NotNull final TypeWriter<T> writer)
    {
        class TypeCodec implements JsonSerializer<T>, JsonDeserializer<T>
        {

            @Override
            public T deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
            {
                return reader.deserialize(json, typeOfT, context);
            }

            @Override
            public JsonElement serialize(final T src, final Type typeOfSrc, final JsonSerializationContext context)
            {
                return writer.serialize(src, typeOfSrc, context);
            }

        }

        ADAPTERS.put(clazz, new TypeCodec());

        if (refresh)
        {
            refreshGsonInstances();
        }
    }


    public static <T> void register(@NotNull final Class<T> clazz, @NotNull final TypeReader.Raw<T> reader, @NotNull final TypeWriter.Raw<T> writer)
    {
        register(clazz, true, reader, writer);
    }

    public static <T> void register(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final TypeReader.Raw<T> reader, @NotNull final TypeWriter.Raw<T> writer)
    {
        register(clazz, refresh, reader, ((TypeWriter<T>) writer));
    }


    public static <T, C extends JsonSerializer<T> & JsonDeserializer<T>> void register(@NotNull final Class<T> clazz, @NotNull final C codec)
    {
        register(clazz, true, codec);
    }

    public static <T, C extends JsonSerializer<T> & JsonDeserializer<T>> void register(@NotNull final Class<T> clazz, final boolean refresh, @NotNull final C codec)
    {
        ADAPTERS.put(clazz, codec);

        if (refresh)
        {
            refreshGsonInstances();
        }
    }

}
