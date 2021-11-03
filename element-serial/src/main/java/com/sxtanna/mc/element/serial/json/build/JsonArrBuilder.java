package com.sxtanna.mc.element.serial.json.build;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Collection;

public record JsonArrBuilder(@NotNull JsonArray json)
{

    public JsonArrBuilder()
    {
        this(new JsonArray());
    }


    @Contract("_ -> this")
    public @NotNull JsonArrBuilder add(@NotNull final JsonElement json)
    {
        this.json().add(json);
        return this;
    }

    @Contract("_ -> this")
    public @NotNull JsonArrBuilder add(@NotNull final JsonElement... json)
    {
        for (final var element : json)
        {
            add(element);
        }
        return this;
    }

    @Contract("_ -> this")
    public @NotNull JsonArrBuilder add(@NotNull final Collection<JsonElement> json)
    {
        json.forEach(this::add);
        return this;
    }

}