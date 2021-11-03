package com.sxtanna.mc.element.bukkit.keys;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import com.sxtanna.mc.element.serial.json.Json;

import com.google.gson.JsonElement;

import java.util.UUID;
import java.util.function.BiFunction;

public enum Persistent
{
    ;

    @NotNull
    public static final PersistentDataType<String, UUID> UUID =
            type(String.class,
                 UUID.class,
                 (c, $) -> c.toString(),
                 (p, $) -> java.util.UUID.fromString(p));

    @NotNull
    public static final PersistentDataType<Byte, Boolean> BOOL =
            type(Byte.class,
                 Boolean.class,
                 (c, $) -> (byte) (c ? 1 : 0),
                 (p, $) -> p != 0);

    @NotNull
    public static final PersistentDataType<String, JsonElement> JSON =
            type(String.class,
                 JsonElement.class,
                 (c, $) -> Json.encodeToText(c),
                 (p, $) -> Json.parse(p));


    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <P, C> @NotNull PersistentDataType<P, C> type(@NotNull final Class<P> pClazz,
                                                                @NotNull final Class<C> cClazz,

                                                                @NotNull final BiFunction<C, PersistentDataAdapterContext, P> pFunction,
                                                                @NotNull final BiFunction<P, PersistentDataAdapterContext, C> cFunction)
    {
        return new PersistentDataType<>()
        {
            @Override
            public @NotNull Class<P> getPrimitiveType()
            {
                return pClazz;
            }

            @Override
            public @NotNull Class<C> getComplexType()
            {
                return cClazz;
            }

            @Override
            public @NotNull P toPrimitive(@NotNull final C complex, @NotNull final PersistentDataAdapterContext context)
            {
                return pFunction.apply(complex, context);
            }

            @Override
            public @NotNull C fromPrimitive(@NotNull final P primitive, @NotNull final PersistentDataAdapterContext context)
            {
                return cFunction.apply(primitive, context);
            }
        };
    }

}
